package com.breaditnow.reservation.application.dto.request;

import java.util.List;

public record ReservationCreateRequest(
        Long bakeryId,
        List<ProductRequest> products
) {
    public record ProductRequest(
            Long productId,
            Integer quantity
    ) {}
}
