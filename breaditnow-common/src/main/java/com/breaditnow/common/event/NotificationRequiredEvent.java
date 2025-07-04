package com.breaditnow.common.event;

import com.breaditnow.common.domain.NotificationType;
import com.breaditnow.common.domain.UserIdentifier;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NotificationRequiredEvent (
        Long reservationId,
        Long bakeryId,
        UserIdentifier recipient,
        UserIdentifier initiator,
        NotificationType notificationType,
        String customerNickName,
        String bakeryName,
        List<String> productNames,
        Long reservationNumber,
        LocalDateTime reservationTime,
        LocalDateTime pickupDeadline,
        String cancelReason
) {
}
