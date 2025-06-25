package com.breaditnow.reservation.application.port;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.application.dto.response.ReservationCreateResponse;
import com.breaditnow.reservation.application.port.in.CreateReservationUseCase;
import com.breaditnow.reservation.application.port.out.BakeryOperationalInfoRepositoryPort;
import com.breaditnow.reservation.application.port.out.ProductInfoRepositoryPort;
import com.breaditnow.reservation.application.port.out.PublishReservationEventPort;
import com.breaditnow.reservation.application.port.out.ReservationRepositoryPort;
import com.breaditnow.reservation.domain.BakeryInfo;
import com.breaditnow.reservation.domain.ProductInfo;
import com.breaditnow.reservation.domain.Reservation;
import com.breaditnow.reservation.domain.ReservationProduct;
import com.breaditnow.reservation.infrastructure.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.breaditnow.reservation.infrastructure.exception.ReservationErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateReservationService implements CreateReservationUseCase {
    private final BakeryOperationalInfoRepositoryPort bakeryInfoPort;
    private final ProductInfoRepositoryPort productInfoPort;
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final PublishReservationEventPort publishReservationEventPort;

    @Override
    @Transactional
    public ReservationCreateResponse create(Long customerId, ReservationCreateRequest request) {
        validateBakeryIsReservable(request.bakeryId());
        List<ProductInfo> productInfos = validateProductsAreReservable(request.reservationProducts());

        List<ReservationProduct> reservationProducts = createReservationProducts(request.reservationProducts(), productInfos);
        Reservation reservation = new Reservation(customerId, request.bakeryId(), reservationProducts);
        Reservation savedReservation = reservationRepositoryPort.save(reservation);

        publishReservationEventPort.publishReservationCreated(ReservationCreatedEvent.from(savedReservation));

        log.info("Reservation created successfully. reservationId: {}", savedReservation.getReservationId());
        return ReservationCreateResponse.from(savedReservation);
    }

    private void validateBakeryIsReservable(Long bakeryId) throws ReservationException {
        BakeryInfo bakeryInfo = bakeryInfoPort.findByBakeryId(bakeryId)
                .orElseThrow(() -> new ReservationException(BAKERY_NOT_FOUND));

        if (!bakeryInfo.isReservable()) {
            throw new ReservationException(BAKERY_NOT_AVAILABLE);
        }
    }

    private List<ProductInfo> validateProductsAreReservable(List<ReservationCreateRequest.ProductRequest> requestedProducts) {
        List<Long> requestedProductIds = requestedProducts.stream().map(ReservationCreateRequest.ProductRequest::productId).toList();
        List<ProductInfo> productInfos = productInfoPort.findByProductIdIn(requestedProductIds);

        if (productInfos.size() != requestedProductIds.size()) {
            throw new ReservationException(PRODUCT_NOT_FOUND);
        }
        if (!productInfos.stream().allMatch(ProductInfo::isReservable)) {
            throw new ReservationException(PRODUCT_NOT_AVAILABLE);
        }
        return productInfos;
    }

    private List<ReservationProduct> createReservationProducts(List<ReservationCreateRequest.ProductRequest> requestedProducts, List<ProductInfo> productInfos) {
        Map<Long, ProductInfo> productInfoMap = productInfos.stream()
                .collect(Collectors.toMap(ProductInfo::getProductId, Function.identity()));

        return requestedProducts.stream()
                .map(p -> {
                    ProductInfo info = productInfoMap.get(p.productId());
                    return new ReservationProduct(info.getProductId(), info.getName(), info.getImageUrl(), new Money(info.getPrice()), p.quantity());
                })
                .collect(Collectors.toList());
    }
}
