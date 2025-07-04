package com.breaditnow.common.event;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.dto.StockUpdateItem;

import java.util.List;

public record StockUpdateResultEvent(
        Long reservationId,
        UserIdentifier initiator,
        ReservationStatus reservationStatus,
        List<StockUpdateItem> stockUpdateItems,
        Status status,
        String message
) {
    public enum Status {
        SUCCESS, FAILURE
    }
}
