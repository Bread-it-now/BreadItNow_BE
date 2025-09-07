package com.breaditnow.reservation.adapter.out.messaging;

import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.reservation.domain.port.out.NotificationEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.messaging.RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE;
import static com.breaditnow.common.messaging.RabbitMQConstants.ROUTING_KEY_NOTIFICATION_SEND_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventRabbitMqAdapter implements NotificationEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(NotificationRequiredEvent event) {
        final String routingKey = ROUTING_KEY_NOTIFICATION_SEND_REQUEST;

        rabbitTemplate.convertAndSend(BREADITNOW_TOPIC_EXCHANGE, routingKey, event);
    }
}
