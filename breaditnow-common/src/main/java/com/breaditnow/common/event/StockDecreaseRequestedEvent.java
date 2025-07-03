package com.breaditnow.common.event;

import com.breaditnow.common.dto.StockUpdateItem;

import java.util.List;

public record StockDecreaseRequestedEvent(
        Long reservationId,
        List<StockUpdateItem> stockUpdateItems
) {
}
