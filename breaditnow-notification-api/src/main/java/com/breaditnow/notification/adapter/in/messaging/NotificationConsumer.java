package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.notification.adapter.in.dto.ReservationCreatedEvent;
import com.breaditnow.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "notification.reservation.queue")
    public void handleReservationCreated(ReservationCreatedEvent event) {
        log.info("Received reservation created event: {}", event);
        notificationService.sendReservationNotification(event);
    }
}
