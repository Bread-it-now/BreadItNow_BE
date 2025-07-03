package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.notification.adapter.in.dto.ReservationStatusChangedEvent;
import com.breaditnow.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_RESERVATION_QUEUE)
    public void handleReservationStatusChanged(ReservationStatusChangedEvent event) {
        log.info("Received reservation status changed event: {}", event);
        notificationService.processAndSendNotifications(event);
    }
}
