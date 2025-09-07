package com.breaditnow.reservation.adapter.out.messaging;

import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.common.event.StockIncreaseRequestedEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.reservation.domain.port.out.ReservationEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventRabbitMqAdapter implements ReservationEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishStockDecreaseRequest(StockDecreaseRequestedEvent event) {
        final String routingKey = RabbitMQConstants.ROUTING_KEY_STOCK_DECREASE_REQUEST;
        rabbitTemplate.convertAndSend(RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE, routingKey, event);
    }

    @Override
    public void publishStockIncreaseRequest(StockIncreaseRequestedEvent event) {
        final String routingKey = RabbitMQConstants.ROUTING_KEY_STOCK_INCREASE_REQUEST;
        rabbitTemplate.convertAndSend(RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE, routingKey, event);
    }
}
