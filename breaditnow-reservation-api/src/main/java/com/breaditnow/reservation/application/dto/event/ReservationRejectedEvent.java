package com.breaditnow.reservation.application.dto.event;

import com.breaditnow.reservation.domain.Reservation;

public record ReservationRejectedEvent(
        Long reservationId,
        Long bakeryId,
        Long userId
) {
    public static ReservationRejectedEvent from(Reservation reservation) {
        return new ReservationRejectedEvent(reservation.getReservationId(), reservation.getBakeryId(), reservation.getOrdererId());
    }
}
