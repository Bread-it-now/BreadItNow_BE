package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.notification.application.NotificationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.messaging.RabbitMQConstants.QUEUE_NOTIFICATION_SEND_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRequestConsumer {
    private final NotificationHandler notificationHandler;

    @RabbitListener(queues = QUEUE_NOTIFICATION_SEND_REQUEST)
    public void handleNotificationRequest(NotificationRequiredEvent event) {
        notificationHandler.sendNotification(event);
    }
}
