package com.breaditnow.reservation.application.service;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.adapter.in.web.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.adapter.in.web.dto.request.ReservationCreateRequest.ProductRequest;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import com.breaditnow.reservation.domain.port.out.BakeryApiPort;
import com.breaditnow.reservation.domain.port.out.ProductApiPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateReservationService implements CreateReservationUseCase {
    private final BakeryApiPort bakeryApiPort;
    private final ProductApiPort productApiPort;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public Long createReservation(Long customerId, ReservationCreateRequest request) {
        List<Long> productIds = request.reservationProducts().stream()
                .map(ProductRequest::productId)
                .toList();

        Map<Long, ProductInfo> productInfoMap = productApiPort.findProductsByIds(productIds, request.bakeryId());

        BakeryInfo bakeryInfo = bakeryApiPort.findBakeryById(request.bakeryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 빵집입니다."));

        List<ReservationProduct> reservationProducts = request.reservationProducts().stream()
                .map(productRequest -> {
                    ProductInfo productInfo = productInfoMap.get(productRequest.productId());
                    if (productInfo == null) {
                        throw new IllegalArgumentException("상품 정보를 찾을 수 없습니다: ID " + productRequest.productId());
                    }
                    return new ReservationProduct(productInfo.productId(), productInfo.name(), productInfo.imageUrl(), new Money(productInfo.price()), productRequest.quantity());
                })
                .toList();

        Reservation reservation = new Reservation(customerId, bakeryInfo.bakeryId(), reservationProducts);
        Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation.getReservationId();
    }
}
