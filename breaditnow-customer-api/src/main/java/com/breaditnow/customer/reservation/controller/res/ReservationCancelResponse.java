package com.breaditnow.customer.reservation.controller.res;

import com.breaditnow.domain.domain.reservation.entity.Reservation;

public record ReservationCancelResponse(
        Long reservationId,
        String status
) {
    public static ReservationCancelResponse of(Reservation reservation) {
        return new ReservationCancelResponse(
                reservation.getId(),
                reservation.getStatus().name()
        );
    }
}
