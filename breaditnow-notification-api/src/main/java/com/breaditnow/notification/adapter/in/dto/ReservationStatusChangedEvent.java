package com.breaditnow.notification.adapter.in.dto;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.domain.Role;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationStatusChangedEvent(
        Long reservationId,
        Long bakeryId,
        Long ownerId,
        Long customerId,
        String bakeryName,
        String customerNickname,
        List<String> productNames,
        Integer totalPrice,
        ReservationStatus reservationStatus,
        Long reservationNumber,
        LocalDateTime pickupDeadline,
        Role initiatedBy,
        String cancelReason,
        LocalDateTime eventOccurredAt
) {
}
