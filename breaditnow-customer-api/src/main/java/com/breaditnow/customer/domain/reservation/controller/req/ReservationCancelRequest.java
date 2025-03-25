package com.breaditnow.customer.domain.reservation.controller.req;

import jakarta.validation.constraints.NotBlank;

public record ReservationCancelRequest(
        @NotBlank String reason
) {

}
