package com.breaditnow.owner.bakery.infrastructure.adapter.out.event.rabbitmq;

import com.breaditnow.owner.bakery.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryInfoUpdatedEvent;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.common.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BakeryEventPublisherAdapter implements PublishBakeryEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishBakeryUpdatedEvent(Bakery bakery) {
        BakeryInfoUpdatedEvent event = BakeryInfoUpdatedEvent.from(bakery);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BAKERY_INFO_EXCHANGE, "", event);
    }

    @Override
    public void publishBakeryDeleted(Long bakeryId) {
        BakeryDeletedEvent event = new BakeryDeletedEvent(bakeryId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BAKERY_INFO_EXCHANGE, "", event);
    }
}
