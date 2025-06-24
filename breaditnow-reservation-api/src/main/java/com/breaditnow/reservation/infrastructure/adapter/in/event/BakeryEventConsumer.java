package com.breaditnow.reservation.infrastructure.adapter.in.event;

import com.breaditnow.reservation.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryOperatingStatusChangedEvent;
import com.breaditnow.reservation.application.port.in.BakeryInfoSynchronizationUseCase;
import com.breaditnow.reservation.application.port.in.DeleteBakerySyncDataUseCase;
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
    private final DeleteBakerySyncDataUseCase deleteUseCase;

    @RabbitListener(queues = RabbitMQConfig.CREATE_QUEUE)
    public void handleBakeryCreated(BakeryCreatedEvent event) {
        log.info("<<< Received BakeryCreatedEvent for bakeryId: {}", event.bakeryId());
        syncUseCase.createBakeryRecord(event);
    }

    @RabbitListener(queues = RabbitMQConfig.STATUS_UPDATE_QUEUE)
    public void handleBakeryUpdate(BakeryOperatingStatusChangedEvent event) {
        log.info("<<< Received BakeryInfoUpdatedEvent for bakeryId: {}", event.bakeryId());
        syncUseCase.synchronizeStatus(event);
    }

    @RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
    public void handleBakeryDelete(BakeryDeletedEvent event) {
        log.info("<<< Received BakeryDeletedEvent for bakeryId: {}", event.bakeryId());
        deleteUseCase.delete(event.bakeryId());
    }
}
