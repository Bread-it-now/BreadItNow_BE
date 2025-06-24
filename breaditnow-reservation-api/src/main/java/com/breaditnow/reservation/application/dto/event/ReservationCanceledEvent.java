package com.breaditnow.reservation.application.dto.event;

import com.breaditnow.reservation.domain.Reservation;

public record ReservationCanceledEvent(
        Long reservationId,
        Long bakeryId,
        String reason
) {
    public static ReservationCanceledEvent from(Reservation reservation) {
        return new ReservationCanceledEvent(
                reservation.getReservationId(),
                reservation.getBakeryId(),
                reservation.getReservationState().getCancelReason()
        );
    }
}
