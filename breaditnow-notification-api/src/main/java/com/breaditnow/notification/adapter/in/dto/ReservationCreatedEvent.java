package com.breaditnow.notification.adapter.in.dto;

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

