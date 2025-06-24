package com.breaditnow.reservation.infrastructure.adapter.in.event;

import com.breaditnow.reservation.application.dto.event.ProductCreatedEvent;
import com.breaditnow.reservation.application.dto.event.ProductDeletedEvent;
import com.breaditnow.reservation.application.dto.event.ProductUpdatedEvent;
import com.breaditnow.reservation.application.port.in.ProductInfoSynchronizationUseCase;
import com.breaditnow.reservation.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final ProductInfoSynchronizationUseCase useCase;

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATED_QUEUE)
    public void handleProductCreated(ProductCreatedEvent event) {
        log.info("<<< Received ProductCreatedEvent for productId: {}", event.productId());
        useCase.createProductInfo(event);
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_UPDATED_QUEUE)
    public void handleProductUpdated(ProductUpdatedEvent event) {
        log.info("<<< Received ProductUpdatedEvent for productId: {}", event.productId());
        useCase.updateProductInfo(event);
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_DELETED_QUEUE)
    public void handleProductDeleted(ProductDeletedEvent event) {
        log.info("<<< Received ProductDeletedEvent for productId: {}", event.productId());
        useCase.deleteProductInfo(event);
    }
}
