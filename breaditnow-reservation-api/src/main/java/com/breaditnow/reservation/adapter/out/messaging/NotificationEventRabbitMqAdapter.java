package com.breaditnow.reservation.adapter.out.messaging;

import com.breaditnow.common.event.NotificationSendRequestedEvent;
import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.reservation.domain.port.out.NotificationEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventRabbitMqAdapter implements NotificationEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(NotificationSendRequestedEvent event) {
        final String routingKey = "v1.notification.send.requested";
        log.info("알림 발송 요청 이벤트 발행: Exchange [{}], RoutingKey [{}], 예약 ID [{}]",
                RabbitMQConfig.BREADITNOW_EXCHANGE, routingKey, event.reservationId());

        rabbitTemplate.convertAndSend(RabbitMQConfig.BREADITNOW_EXCHANGE, routingKey, event);
    }
}
