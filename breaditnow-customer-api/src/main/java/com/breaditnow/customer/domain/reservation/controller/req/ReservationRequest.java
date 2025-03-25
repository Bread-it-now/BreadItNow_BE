package com.breaditnow.customer.domain.reservation.controller.req;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReservationRequest(
        @NotNull Long bakeryId,
        @NotNull List<ReservationProductRequest> reservationProducts
) {
}
