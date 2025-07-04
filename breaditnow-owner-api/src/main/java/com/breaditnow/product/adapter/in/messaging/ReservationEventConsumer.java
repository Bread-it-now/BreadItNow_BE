package com.breaditnow.product.adapter.in.messaging;

import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.common.event.StockIncreaseRequestedEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.product.application.ProductStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventConsumer {
    private final ProductStockService productStockService;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK_DECREASE_REQUEST)
    public void handleStockDecreaseRequest(StockDecreaseRequestedEvent event) {
        log.info("재고 감소 요청 수신: 예약 ID [{}]", event.reservationId());
        productStockService.decreaseStock(event.reservationId(), event.initiator(), event.stockUpdateItems());
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK_INCREASE_REQUEST)
    public void handleStockIncreaseRequest(StockIncreaseRequestedEvent event) {
        log.info("재고 증가 요청 수신: 예약 ID [{}]", event.reservationId());
        productStockService.increaseStock(event.reservationId(), event.initiator(), event.stockUpdateItems(), event.cancelReason());
    }
}
