package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.common.event.StockIncreaseRequestedEvent;
import com.breaditnow.reservation.application.event.ReservationStatusChangedEvent;

public interface ReservationEventPort {
    void publish(ReservationStatusChangedEvent event);
    void publishStockDecreaseRequest(StockDecreaseRequestedEvent event);
    void publishStockIncreaseRequest(StockIncreaseRequestedEvent event);
}
