package com.breaditnow.reservation.application.event;

import com.breaditnow.reservation.domain.model.Reservation;

public record ReservationCreatedEvent(
        Reservation reservation,
        Long ownerId
) {
}
