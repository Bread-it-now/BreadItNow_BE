package com.breaditnow.reservation.adapter.in.messaging;

import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.reservation.application.ReservationResultHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockIncreaseResultConsumer {
    private final ReservationResultHandler handler;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK_INCREASE_RESULT)
    public void handleStockIncreaseResult(StockUpdateResultEvent resultEvent) {
        handler.finalizeCancellation(resultEvent);
    }
}
