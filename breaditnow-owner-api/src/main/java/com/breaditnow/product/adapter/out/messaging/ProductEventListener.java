package com.breaditnow.product.adapter.out.messaging;

import com.breaditnow.common.domain.OperationType;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.product.application.event.ProductStockUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.breaditnow.common.messaging.RabbitMQConstants.ROUTING_KEY_STOCK_RESULT_FORMAT;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {
    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStockUpdateResult(ProductStockUpdatedEvent wrapperEvent) {
        StockUpdateResultEvent event = wrapperEvent.resultEvent();
        OperationType operationType = wrapperEvent.operationType();

        String routingKey = String.format(ROUTING_KEY_STOCK_RESULT_FORMAT, operationType.getValue(), event.status().name().toLowerCase());

        rabbitTemplate.convertAndSend(RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE, routingKey, event);
    }
}
