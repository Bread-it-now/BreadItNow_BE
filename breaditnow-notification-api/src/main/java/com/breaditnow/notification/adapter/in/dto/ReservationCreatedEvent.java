package com.breaditnow.notification.adapter.in.dto;

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

