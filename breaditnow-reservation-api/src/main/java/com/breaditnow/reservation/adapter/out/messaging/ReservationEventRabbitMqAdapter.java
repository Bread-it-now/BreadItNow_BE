package com.breaditnow.reservation.adapter.out.messaging;

import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.reservation.application.event.ReservationCreatedEvent;
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
    public void publish(ReservationCreatedEvent event) {
        log.info("Publishing reservation created event to RabbitMQ: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BREADITNOW_EXCHANGE,
                RabbitMQConfig.RESERVATION_CREATED_ROUTING_KEY,
                event
        );
    }
}
