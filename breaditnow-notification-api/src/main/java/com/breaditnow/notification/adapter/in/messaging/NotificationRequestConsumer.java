package com.breaditnow.notification.adapter.in.messaging;

import com.breaditnow.common.event.NotificationSendRequestedEvent;
import com.breaditnow.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRequestConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "notification.send-request.queue")
    public void handleNotificationRequest(NotificationSendRequestedEvent event) {
        log.info("알림 발송 요청 수신: 예약 ID [{}]", event.reservationId());
        notificationService.sendNotification(event);
    }
}
