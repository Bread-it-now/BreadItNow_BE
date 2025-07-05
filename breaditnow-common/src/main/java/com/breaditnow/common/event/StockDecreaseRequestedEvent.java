package com.breaditnow.common.event;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.dto.StockUpdateItem;
import com.breaditnow.common.domain.UserIdentifier;

import java.util.List;

public record StockDecreaseRequestedEvent(
        Long reservationId,
        UserIdentifier initiator,
        ReservationStatus reservationStatus,
        List<StockUpdateItem> stockUpdateItems,
        String cancelReason
) {
}
