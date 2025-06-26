package com.breaditnow.bakery.adapter.out.messaging;

import com.breaditnow.bakery.application.event.BakeryCreatedEvent;
import com.breaditnow.bakery.application.event.BakeryDeletedEvent;
import com.breaditnow.bakery.application.event.BakeryUpdatedEvent;
import com.breaditnow.bakery.domain.port.out.BakeryEventPublisherPort;
import com.breaditnow.common.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BakeryRabbitMqPublisher implements BakeryEventPublisherPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishBakeryCreatedEvent(BakeryCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BAKERY_EVENT_EXCHANGE,
                RabbitMQConfig.CREATE_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishBakeryUpdatedEvent(BakeryUpdatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BAKERY_EVENT_EXCHANGE,
                RabbitMQConfig.STATUS_UPDATE_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishBakeryDeleteEvent(BakeryDeletedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BAKERY_EVENT_EXCHANGE,
                RabbitMQConfig.DELETE_ROUTING_KEY,
                event
        );
    }
}