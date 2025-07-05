package com.breaditnow.reservation.adapter.in.messaging;

import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.reservation.application.ReservationResultHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.event.StockUpdateResultEvent.Status.FAILURE;
import static com.breaditnow.common.event.StockUpdateResultEvent.Status.SUCCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockDecreaseResultConsumer {
    private final ReservationResultHandler handler;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK_DECREASE_RESULT)
    public void handleStockDecreaseResult(StockUpdateResultEvent resultEvent) {
        log.info("재고 감소 결과 수신: {}", resultEvent);
        if (resultEvent.status() == SUCCESS) {
            handler.finalizeApproval(resultEvent);
        }
        else if(resultEvent.status() == FAILURE) {
            handler.handleApprovalFailure(resultEvent);
        }
    }
}
