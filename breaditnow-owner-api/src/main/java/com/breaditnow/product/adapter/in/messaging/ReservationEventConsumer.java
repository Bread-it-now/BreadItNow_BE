package com.breaditnow.product.adapter.in.messaging;

import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.product.application.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationEventConsumer {
    private final ProductStockService productStockService;

    @RabbitListener(queues = "product.stock-decrease.queue")
    public void handleStockDecreaseRequest(StockDecreaseRequestedEvent event) {
        productStockService.decreaseStock(event.reservationId(), event.stockUpdateItems());
    }
}
