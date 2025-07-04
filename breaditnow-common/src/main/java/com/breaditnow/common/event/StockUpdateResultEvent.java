package com.breaditnow.common.event;

import com.breaditnow.common.domain.UserIdentifier;

public record StockUpdateResultEvent(
        Long reservationId,
        UserIdentifier initiator,
        Status status,
        String message
) {
    public enum Status {
        SUCCESS, FAILURE
    }
}
