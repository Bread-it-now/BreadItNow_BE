package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.notification.adapter.in.dto.ReservationCreatedEvent;
import com.breaditnow.notification.application.NotificationCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationCreationService notificationCreationService;

    @RabbitListener(queues = "notification.reservation.queue")
    public void handleReservationCreated(ReservationCreatedEvent event) {
        notificationCreationService.handleReservationCreation(event);
    }
}
