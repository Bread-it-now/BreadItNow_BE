package com.breaditnow.reservation.infrastructure.adapter.in.event;

import com.breaditnow.reservation.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryUpdatedEvent;
import com.breaditnow.reservation.application.port.in.BakeryInfoSynchronizationUseCase;
import com.breaditnow.reservation.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BakeryEventConsumer {
    private final BakeryInfoSynchronizationUseCase syncUseCase;

    @RabbitListener(queues = RabbitMQConfig.BAKERY_CREATED_QUEUE)
    public void handleBakeryCreated(BakeryCreatedEvent event) {
        log.info("<<< Received BakeryCreatedEvent for bakeryId: {}", event.bakeryId());
        syncUseCase.createBakeryInfo(event);
    }

    @RabbitListener(queues = RabbitMQConfig.BAKERY_STATUS_CHANGED_QUEUE)
    public void handleBakeryUpdate(BakeryUpdatedEvent event) {
        log.info("<<< Received BakeryInfoUpdatedEvent for bakeryId: {}", event.bakeryId());
        syncUseCase.updateBakeryInfo(event);
    }

    @RabbitListener(queues = RabbitMQConfig.BAKERY_DELETED_QUEUE)
    public void handleBakeryDelete(BakeryDeletedEvent event) {
        log.info("<<< Received BakeryDeletedEvent for bakeryId: {}", event.bakeryId());
        syncUseCase.deleteBakeryInfo(event);
    }
}
