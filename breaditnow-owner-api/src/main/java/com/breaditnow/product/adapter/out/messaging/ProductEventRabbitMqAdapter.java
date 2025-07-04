package com.breaditnow.product.adapter.out.messaging;

import com.breaditnow.common.domain.OperationType;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.product.domain.port.out.ProductEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventRabbitMqAdapter implements ProductEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishStockUpdateResult(StockUpdateResultEvent event, OperationType operationType) {
        String routingKey = String.format(
                RabbitMQConstants.ROUTING_KEY_STOCK_RESULT_FORMAT,
                operationType.getValue(),
                event.status().name().toLowerCase()
        );

        log.info("재고 처리 결과 발행: Exchange [{}], RoutingKey [{}], 예약 ID [{}]",
                RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE, routingKey, event.reservationId());

        rabbitTemplate.convertAndSend(RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE, routingKey, event);
    }
}
