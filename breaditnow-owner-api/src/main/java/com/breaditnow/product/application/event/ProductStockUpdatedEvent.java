package com.breaditnow.product.application.event;

import com.breaditnow.common.domain.OperationType;
import com.breaditnow.common.event.StockUpdateResultEvent;

public record ProductStockUpdatedEvent(
        StockUpdateResultEvent resultEvent,
        OperationType operationType
) {
}
