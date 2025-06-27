package com.breaditnow.customer.reservation.application.dto.request;

import java.util.List;

public record ReservationCreateRequest(
        Long bakeryId,
        List<ProductRequest> reservationProducts
) {
    public record ProductRequest(
            Long productId,
            Integer quantity
    ) {}
}
