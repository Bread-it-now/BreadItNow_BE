package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.common.event.StockIncreaseRequestedEvent;

public interface ReservationEventPort {
    void publishStockDecreaseRequest(StockDecreaseRequestedEvent event);
    void publishStockIncreaseRequest(StockIncreaseRequestedEvent event);
}
