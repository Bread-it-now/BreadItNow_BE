package com.breaditnow.common.event;

import com.breaditnow.common.domain.NotificationTypeDto;
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
        NotificationTypeDto notificationTypeDto,
        String customerNickName,
        String bakeryName,
        List<String> productNames,
        Long reservationNumber,
        LocalDateTime reservationTime,
        LocalDateTime pickupDeadline,
        String cancelReason
) {
}
