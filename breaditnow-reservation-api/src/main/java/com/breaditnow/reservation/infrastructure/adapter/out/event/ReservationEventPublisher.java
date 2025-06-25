package com.breaditnow.reservation.infrastructure.adapter.out.event;

import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;
import com.breaditnow.reservation.application.port.out.PublishReservationEventPort;
import com.breaditnow.reservation.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventPublisher implements PublishReservationEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishReservationCreated(ReservationCreatedEvent event) {
        log.info(">>> Publishing ReservationCreatedEvent for reservationId: {}", event.reservationId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVATION_EVENT_EXCHANGE,
                RabbitMQConfig.RESERVATION_CREATED_ROUTING_KEY,
                event
        );
    }
}
