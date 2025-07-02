package com.breaditnow.reservation.application.dto.request;

import java.util.List;

public record ReservationPartialApproveRequest(
        List<ProductRequest> reservationProducts,
        String reason
) {
    public record ProductRequest(
            Long productId,
            Integer quantity
    ) {}
}



