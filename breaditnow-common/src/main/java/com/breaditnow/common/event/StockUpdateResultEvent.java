package com.breaditnow.common.event;

import com.breaditnow.common.dto.UserIdentifier;

public record StockUpdateResultEvent(
        Long reservationId,
        UserIdentifier userIdentifier,
        Status status,
        String message
) {
    public enum Status {
        SUCCESS, FAILURE
    }
}
