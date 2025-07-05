package com.breaditnow.reservation.application.event;

import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.reservation.domain.model.Reservation;

public record CancellationNotificationRequiredEvent(
        Reservation reservation,
        UserIdentifier initiator,
        Long ownerId,
        String reason
) {}
