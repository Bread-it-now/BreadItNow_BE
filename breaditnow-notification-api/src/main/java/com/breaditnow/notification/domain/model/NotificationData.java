package com.breaditnow.notification.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NotificationData(
        String customerNickname,
        String bakeryName,
        Long reservationNumber,
        List<String> productNames,
        LocalDateTime reservationTime,
        LocalDateTime pickupDeadline,
        String cancelReason
) {
}
