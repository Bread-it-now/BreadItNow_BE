package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationErrorCode;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest.ProductRequest;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.application.event.ReservationCreatedEvent;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.exception.ReservationErrorCode.BAKERY_NOT_FOUND;
import static com.breaditnow.common.exception.ReservationErrorCode.PRODUCT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class CreateReservationService implements CreateReservationUseCase {
    private final OwnerApiPort ownerApiPort;
    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Long createReservation(AuthenticatedUser user, ReservationCreateRequest request) {
        if(Role.fromString(user.role()) != CUSTOMER){
            throw new ReservationException(ReservationErrorCode.UNAUTHORIZED_ACCESS);
        }

        BakeryInfo bakeryInfo = getBakeryInfo(request.bakeryId());
        Map<Long, ProductInfo> productInfoMap = getProductInfoMap(request, bakeryInfo.bakeryId());
        List<ReservationProduct> reservationProducts = toReservationProducts(request, productInfoMap);

        Reservation reservation = new Reservation(user.userId(), bakeryInfo.bakeryId(), bakeryInfo.name(), reservationProducts);
        Reservation savedReservation = reservationRepository.save(reservation);

        publishReservationCreatedEvent(savedReservation, user.userId(), bakeryInfo, reservationProducts);

        return savedReservation.getReservationId();
    }

    private BakeryInfo getBakeryInfo(Long bakeryId) {
        return ownerApiPort.findBakeryById(bakeryId)
                .orElseThrow(() -> new ReservationException(BAKERY_NOT_FOUND));
    }

    private Map<Long, ProductInfo> getProductInfoMap(ReservationCreateRequest request, Long bakeryId) {
        List<Long> productIds = request.reservationProducts().stream()
                .map(ProductRequest::productId)
                .toList();
        return ownerApiPort.findProductsByIds(productIds, bakeryId);
    }

    private List<ReservationProduct> toReservationProducts(ReservationCreateRequest request, Map<Long, ProductInfo> productInfoMap) {
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

    private void publishReservationCreatedEvent(Reservation reservation, Long customerId, BakeryInfo bakeryInfo, List<ReservationProduct> reservationProducts) {
        eventPublisher.publishEvent(new ReservationCreatedEvent(
                reservation.getReservationId(),
                customerId,
                bakeryInfo.ownerId(),
                bakeryInfo.name(),
                reservationProducts.stream().map(ReservationProduct::getProductName).toList(),
                reservation.getTotalPrice().getAmount()
        ));
    }
}
