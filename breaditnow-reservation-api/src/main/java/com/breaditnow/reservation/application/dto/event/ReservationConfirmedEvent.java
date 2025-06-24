package com.breaditnow.reservation.application.dto.event;

import com.breaditnow.reservation.domain.Reservation;

public record ReservationConfirmedEvent(
        Long reservationId,
        Long bakeryId,
        Long userId
) {
    public static ReservationConfirmedEvent from(Reservation reservation) {
        return new ReservationConfirmedEvent(reservation.getReservationId(), reservation.getBakeryId(), reservation.getOrdererId());
    }
}
