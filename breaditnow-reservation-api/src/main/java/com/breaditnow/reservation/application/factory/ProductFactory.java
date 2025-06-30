package com.breaditnow.reservation.application.factory;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.application.dto.request.MyReservationCreateRequest;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;
import com.breaditnow.reservation.application.provider.ProductProvider;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductFactory {
    private final ProductProvider productProvider;

    public List<ReservationProduct> createFrom(ReservationPartialApproveRequest request, Long bakeryId) {
        Map<Long, Integer> quantityMap = request.reservationProducts().stream()
                .collect(Collectors.toMap(
                        ReservationPartialApproveRequest.ProductRequest::productId,
                        ReservationPartialApproveRequest.ProductRequest::quantity
                ));

        List<Long> productIds = request.reservationProducts().stream()
                .map(ReservationPartialApproveRequest.ProductRequest::productId)
                .toList();

        List<ProductInfo> productInfos = productProvider.provideAllByIds(productIds, bakeryId);

        return productInfos.stream()
                .map(productInfo -> new ReservationProduct(
                            productInfo.productId(),
                            productInfo.name(),
                            productInfo.imageUrl(),
                            new Money(productInfo.price()),
                            quantityMap.get(productInfo.productId())
                ))
                .toList();
    }

    public List<ReservationProduct> createFrom(MyReservationCreateRequest request, Long bakeryId) {
        Map<Long, Integer> quantityMap = request.reservationProducts().stream()
                .collect(Collectors.toMap(
                        MyReservationCreateRequest.ProductRequest::productId,
                        MyReservationCreateRequest.ProductRequest::quantity
                ));

        List<Long> productIds = request.reservationProducts().stream()
                .map(MyReservationCreateRequest.ProductRequest::productId)
                .toList();

        List<ProductInfo> productInfos = productProvider.provideAllByIds(productIds, bakeryId);

        return productInfos.stream()
                .map(productInfo -> new ReservationProduct(
                        productInfo.productId(),
                        productInfo.name(),
                        productInfo.imageUrl(),
                        new Money(productInfo.price()),
                        quantityMap.get(productInfo.productId())
                ))
                .toList();
    }

}
