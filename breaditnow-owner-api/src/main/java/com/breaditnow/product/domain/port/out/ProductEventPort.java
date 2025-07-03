package com.breaditnow.product.domain.port.out;

import com.breaditnow.common.event.StockUpdateResultEvent;

public interface ProductEventPort {
    void publishStockUpdateResult(StockUpdateResultEvent event);
}
