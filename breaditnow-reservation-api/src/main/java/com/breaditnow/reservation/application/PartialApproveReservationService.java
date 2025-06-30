package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.OperatingStatus;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest.ProductRequest;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.in.PartialApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.breaditnow.common.exception.ReservationErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PartialApproveReservationService implements PartialApproveReservationUseCase {
    private final OwnerApiPort ownerApiPort;
    private final ReservationRepository reservationRepository;

    @Override
    public void partialApproveReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationPartialApproveRequest request) {
        if(!user.isOwner()){
            throw new ReservationException(FORBIDDEN_ACCESS);
        }

        BakeryInfo bakeryInfo = ownerApiPort.findBakeryById(bakeryId)
                .orElseThrow(() -> new ReservationException(BAKERY_NOT_FOUND));

        if (bakeryInfo.deleted()) {
            throw new ReservationException(BAKERY_IS_DELETED);
        }
        if (bakeryInfo.operatingStatus() != OperatingStatus.OPEN) {
            throw new ReservationException(BAKERY_IS_NOT_OPENED);
        }
        if (!bakeryInfo.ownerId().equals(user.userId())) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }

        Map<Long, ProductInfo> productInfoMap = getProductInfoMap(request, bakeryInfo.bakeryId());
        List<ReservationProduct> reservationProducts = toReservationProducts(request, productInfoMap);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        Long newReservationNumber = reservationRepository.findLastOfBakeryForToday(bakeryId)
                .map(lastReservation -> lastReservation.getReservationNumber() + 1)
                .orElse(1L);

        reservation.partialApprove(reservationProducts, newReservationNumber);
        reservationRepository.save(reservation);
    }

    private Map<Long, ProductInfo> getProductInfoMap(ReservationPartialApproveRequest request, Long bakeryId) {
        List<Long> productIds = request.reservationProducts().stream()
                .map(ProductRequest::productId)
                .toList();
        return ownerApiPort.findProductsByIds(productIds, bakeryId);
    }

    private List<ReservationProduct> toReservationProducts(ReservationPartialApproveRequest request, Map<Long, ProductInfo> productInfoMap) {
        return request.reservationProducts().stream()
                .map(productRequest -> {
                    ProductInfo productInfo = Optional.ofNullable(productInfoMap.get(productRequest.productId()))
                            .orElseThrow(() -> new ReservationException(PRODUCT_NOT_FOUND));
                    return new ReservationProduct(
                            productInfo.productId(),
                            productInfo.name(),
                            productInfo.imageUrl(),
                            new Money(productInfo.price()),
                            productRequest.quantity()
                    );
                })
                .toList();
    }
}
