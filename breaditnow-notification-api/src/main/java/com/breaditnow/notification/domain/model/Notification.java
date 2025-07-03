package com.breaditnow.notification.domain.model;

import com.breaditnow.common.exception.NotificationException;
import lombok.Builder;
import lombok.Getter;

import static com.breaditnow.common.exception.NotificationErrorCode.ALREADY_DELETED_NOTIFICATION;
import static com.breaditnow.common.exception.NotificationErrorCode.ALREADY_READ_NOTIFICATION;

@Getter
public class Notification {
    private Long notificationId;
    private Long reservationId;
    private Long bakeryId;

    private NotificationActor recipient;
    private NotificationActor initiator;

    private NotificationType notificationType;
    private String content;
    private boolean isRead;
    private boolean isDeleted;

    @Builder
    public Notification(Long notificationId, Long reservationId, Long bakeryId, NotificationActor recipient, NotificationActor initiator, NotificationType notificationType, String content, boolean isRead, boolean isDeleted) {
        this.notificationId = notificationId;
        this.reservationId = reservationId;
        this.bakeryId = bakeryId;
        this.recipient = recipient;
        this.initiator = initiator;
        this.notificationType = notificationType;
        this.content = content;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
    }

    public static Notification create(Long reservationId, Long bakeryId, NotificationActor recipient, NotificationActor initiator, NotificationType notificationType, String content) {
        return Notification.builder()
                .reservationId(reservationId)
                .bakeryId(bakeryId)
                .recipient(recipient)
                .initiator(initiator)
                .notificationType(notificationType)
                .content(content)
                .isRead(false)
                .isDeleted(false)
                .build();
    }

    public void read() {
        if(this.isRead) {
            throw new NotificationException(ALREADY_READ_NOTIFICATION);
        }
        this.isRead = true;
    }

    public void delete() {
        if(this.isDeleted) {
            throw new NotificationException(ALREADY_DELETED_NOTIFICATION);
        }
        this.isDeleted = true;
    }
}
