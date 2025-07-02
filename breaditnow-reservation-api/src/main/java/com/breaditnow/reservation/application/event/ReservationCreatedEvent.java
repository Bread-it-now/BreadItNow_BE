package com.breaditnow.reservation.application.event;

import java.util.List;

public record ReservationCreatedEvent(
        Long reservationId,
        Long customerId,
        Long ownerId,
        String bakeryName,
        List<String> productNames,
        Integer totalPrice
) {
}
