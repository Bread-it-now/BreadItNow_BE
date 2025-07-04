package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.messaging.RabbitMQConstants.QUEUE_NOTIFICATION_SEND_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRequestConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = QUEUE_NOTIFICATION_SEND_REQUEST)
    public void handleNotificationRequest(NotificationRequiredEvent event) {
        log.info("알림 발송 요청 수신: 예약 [{}]", event);
        notificationService.sendNotification(event);
    }
}
