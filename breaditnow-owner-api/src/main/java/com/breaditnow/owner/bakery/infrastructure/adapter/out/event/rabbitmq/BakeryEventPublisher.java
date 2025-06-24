package com.breaditnow.owner.bakery.infrastructure.adapter.out.event.rabbitmq;

import com.breaditnow.owner.bakery.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryUpdatedEvent;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.common.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BakeryEventPublisher implements PublishBakeryEventPort {
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