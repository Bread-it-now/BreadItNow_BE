package com.breaditnow.customer.domain.reservation.controller.res;

import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;

public record ReservationItemResponse(
        Long productId,
        String name,
        int quantity,
        int unitPrice,
        int totalPrice,
        String breadImage
) {
    public static ReservationItemResponse of(ReservationProduct product) {
        return new ReservationItemResponse(
                product.getProduct().getId(),
                product.getProduct().getName(),
                product.getQuantity(),
                product.getUnitPrice(),
                product.getUnitPrice() * product.getQuantity(),
                product.getProduct().getImage()
        );
    }
}
