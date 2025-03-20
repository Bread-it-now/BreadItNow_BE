package com.breaditnow.customer.domain.reservation.controller.req;


import jakarta.validation.constraints.NotNull;

public record ReservationProductRequest(
        @NotNull Long productId,
        @NotNull Integer quantity
) {
}
