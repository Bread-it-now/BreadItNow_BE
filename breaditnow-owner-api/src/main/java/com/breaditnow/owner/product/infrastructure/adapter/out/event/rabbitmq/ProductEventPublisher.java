package com.breaditnow.owner.product.infrastructure.adapter.out.event.rabbitmq;

import com.breaditnow.owner.common.config.RabbitMQConfig;
import com.breaditnow.owner.product.application.port.dto.event.ProductCreatedEvent;
import com.breaditnow.owner.product.application.port.dto.event.ProductDeletedEvent;
import com.breaditnow.owner.product.application.port.dto.event.ProductUpdatedEvent;
import com.breaditnow.owner.product.application.port.out.PublishProductEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventPublisher implements PublishProductEventPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishProductCreated(ProductCreatedEvent event) {
        log.info(">>> Publishing ProductCreatedEvent for productId: {}", event.productId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRODUCT_EVENT_EXCHANGE,
                RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishProductUpdated(ProductUpdatedEvent event) {
        log.info(">>> Publishing ProductUpdatedEvent for productId: {}", event.productId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRODUCT_EVENT_EXCHANGE,
                RabbitMQConfig.PRODUCT_UPDATED_ROUTING_KEY,
                event
        );
    }

    @Override
    public void publishProductDeleted(ProductDeletedEvent event) {
        log.info(">>> Publishing ProductDeletedEvent for productId: {}", event.productId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRODUCT_EVENT_EXCHANGE,
                RabbitMQConfig.PRODUCT_DELETED_ROUTING_KEY,
                event
        );
    }
}
