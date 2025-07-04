package com.breaditnow.notification.application;

import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.notification.application.dto.NotificationTypeMessageUtil;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationMessage;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final FcmNotifier fcmNotifier;

    @Transactional
    public void sendNotification(NotificationRequiredEvent event) {
        try {
            NotificationMessage message = NotificationTypeMessageUtil.createMessage(event);

            Notification notification = Notification.create(
                    event.reservationId(),
                    event.bakeryId(),
                    event.recipient(),
                    event.initiator(),
                    event.notificationType(),
                    message.content()
            );
            notificationRepository.save(notification);
            log.info("알림 이력 저장 완료: 알림 ID [{}]", notification.getNotificationId());

            fcmNotifier.notify(notification.getRecipient(), message.title(), message.content());
            log.info("FCM 알림 발송 완료: 수신자 [{}], 제목 [{}], 내용 [{}]", event.recipient(), message.title(), message.content());
        } catch (Exception e) {
            log.error("알림 처리 중 오류 발생. 예약 ID: [{}], 사유: {}", event.reservationId(), e.getMessage());
        }
    }
}