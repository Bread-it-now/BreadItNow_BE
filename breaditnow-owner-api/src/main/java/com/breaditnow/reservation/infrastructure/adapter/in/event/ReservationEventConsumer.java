package com.breaditnow.reservation.infrastructure.adapter.in.event;

import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;
import com.breaditnow.reservation.application.port.in.ReservationViewSynchronizationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventConsumer {
    private final ReservationViewSynchronizationUseCase useCase;

    @RabbitListener(queues = RabbitMQConfig.RESERVATION_CREATED_QUEUE)
    public void handleReservationCreated(ReservationCreatedEvent event) {
        log.info("<<< Received ReservationCreatedEvent for reservationId: {}", event.reservationId());
        useCase.createReservationView(event);
    }
}
