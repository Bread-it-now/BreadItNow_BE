package com.breaditnow.reservation.adapter.in.web.dto.request;

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
