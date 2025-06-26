package com.breaditnow.reservation.application.dto.request;

import java.util.List;

public record ReservationPartialApproveRequest(
        List<AdjustedProduct> adjustedProducts
) {
    public record AdjustedProduct(
            Long productId,
            Integer quantity
    ) {}
}
