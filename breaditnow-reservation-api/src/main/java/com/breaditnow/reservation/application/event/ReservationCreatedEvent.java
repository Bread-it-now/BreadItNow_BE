package com.breaditnow.reservation.application.event;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationCreatedEvent(
        Long reservationId,
        Long bakeryId,
        Long ownerId,
        String customerName,
        List<String> productNames,
        LocalDateTime reservationTime
) {
}
