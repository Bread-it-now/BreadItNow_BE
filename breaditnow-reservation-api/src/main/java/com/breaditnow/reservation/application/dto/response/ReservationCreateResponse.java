package com.breaditnow.reservation.application.dto.response;

import com.breaditnow.reservation.domain.Reservation;
import com.breaditnow.reservation.domain.ReservationStatus;

public record ReservationCreateResponse(
        Long reservationId,
        ReservationStatus status
) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(reservation.getReservationId(), reservation.getReservationState().getReservationStatus());
    }
}

