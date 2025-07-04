package com.breaditnow.common.event;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.dto.StockUpdateItem;

import java.util.List;

public record StockIncreaseRequestedEvent(
        Long reservationId,
        UserIdentifier initiator,
        List<StockUpdateItem> stockUpdateItems,
        ReservationStatus reservationStatus,
        String cancelReason
) {
}
