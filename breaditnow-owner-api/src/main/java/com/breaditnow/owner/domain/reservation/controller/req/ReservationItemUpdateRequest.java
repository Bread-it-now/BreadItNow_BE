package com.breaditnow.owner.domain.reservation.controller.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationItemUpdateRequest(
        @NotNull Long productId,
        @Min(1) int quantity
) {}
