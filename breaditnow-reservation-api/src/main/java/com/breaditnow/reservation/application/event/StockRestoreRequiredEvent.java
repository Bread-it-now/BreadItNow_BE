package com.breaditnow.reservation.application.event;

import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.reservation.domain.model.Reservation;

public record StockRestoreRequiredEvent(
        Reservation reservation,
        UserIdentifier initiator,
        String reason
) {}
