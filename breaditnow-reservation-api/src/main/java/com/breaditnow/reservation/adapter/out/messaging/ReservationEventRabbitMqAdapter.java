package com.breaditnow.reservation.adapter.out.messaging;

import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.reservation.application.event.ReservationStatusChangedEvent;
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
    public void publish(ReservationStatusChangedEvent event) {
        String routingKey = "reservation.status." + event.reservationStatus().name().toLowerCase();
        log.info("Publishing event with key [{}]: {}", routingKey, event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BREADITNOW_EXCHANGE, routingKey, event);
    }

    private String determineRoutingKey(ReservationStatusChangedEvent event) {
        return switch (event.reservationStatus()) {
            case WAITING -> RabbitMQConfig.RESERVATION_CREATED_ROUTING_KEY;
            case APPROVED -> RabbitMQConfig.RESERVATION_APPROVED_ROUTING_KEY;
            case PARTIAL_APPROVED -> RabbitMQConfig.RESERVATION_PARTIALLY_APPROVED_ROUTING_KEY;
            case CANCELLED -> RabbitMQConfig.RESERVATION_CANCELLED_ROUTING_KEY;
        };
    }
}
