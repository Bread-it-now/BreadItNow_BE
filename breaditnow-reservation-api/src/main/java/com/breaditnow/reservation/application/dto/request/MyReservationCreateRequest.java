package com.breaditnow.reservation.application.dto.request;

import java.util.List;

public record MyReservationCreateRequest(
        Long bakeryId,
        List<ProductRequest> reservationProducts
) {
    public record ProductRequest(
            Long productId,
            Integer quantity
    ) {}
}
