package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.notification.adapter.in.dto.ReservationCreatedEvent;
import com.breaditnow.notification.application.NotificationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationHandler notificationHandler;

    @RabbitListener(queues = "notification.reservation.queue")
    public void handleReservationCreated(ReservationCreatedEvent event) {
        notificationHandler.handleReservationCreation(event);
    }
}
