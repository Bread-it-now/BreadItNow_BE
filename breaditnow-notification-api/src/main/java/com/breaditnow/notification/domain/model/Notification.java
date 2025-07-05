package com.breaditnow.notification.domain.model;

import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.exception.NotificationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.breaditnow.common.exception.NotificationErrorCode.ALREADY_DELETED_NOTIFICATION;
import static com.breaditnow.common.exception.NotificationErrorCode.ALREADY_READ_NOTIFICATION;

@Getter
public class Notification {
    private Long notificationId;
    private Long reservationId;
    private Long bakeryId;

    private UserIdentifier recipient;
    private UserIdentifier initiator;

    private NotificationCategory notificationCategory;
    private NotificationType notificationType;

    private String content;

    private boolean isRead;
    private boolean isDeleted;

    private final LocalDateTime createdAt;

    @Builder
    public Notification(Long notificationId, Long reservationId, Long bakeryId, UserIdentifier recipient, UserIdentifier initiator, NotificationCategory notificationCategory, NotificationType notificationType, String content, boolean isRead, boolean isDeleted, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.reservationId = reservationId;
        this.bakeryId = bakeryId;
        this.recipient = recipient;
        this.initiator = initiator;
        this.notificationCategory = notificationCategory;
        this.notificationType = notificationType;
        this.content = content;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public static Notification create(Long reservationId, Long bakeryId, UserIdentifier recipient, UserIdentifier initiator, NotificationType notificationType, String content) {
        return Notification.builder()
                .reservationId(reservationId)
                .bakeryId(bakeryId)
                .recipient(recipient)
                .initiator(initiator)
                .notificationCategory(notificationType.getCategory())
                .notificationType(notificationType)
                .content(content)
                .isRead(false)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
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
