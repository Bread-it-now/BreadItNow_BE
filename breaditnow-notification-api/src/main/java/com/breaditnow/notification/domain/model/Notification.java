package com.breaditnow.notification.domain.model;

import com.breaditnow.common.exception.NotificationException;
import lombok.Builder;
import lombok.Getter;

import static com.breaditnow.common.exception.NotificationErrorCode.ALREADY_DELETED_NOTIFICATION;
import static com.breaditnow.common.exception.NotificationErrorCode.ALREADY_READ_NOTIFICATION;

@Getter
public class Notification {
    private Long notificationId;
    private Long userId;
    private Long bakeryId;
    private UserType userType;
    private TitleType titleType;
    private NotificationType notificationType;
    private String content;
    private boolean isRead;
    private boolean isDeleted;

    @Builder
    public Notification(Long notificationId, Long userId, Long bakeryId, UserType userType, TitleType titleType, NotificationType notificationType, String content, boolean isRead, boolean isDeleted) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.bakeryId = bakeryId;
        this.userType = userType;
        this.titleType = titleType;
        this.notificationType = notificationType;
        this.content = content;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
    }

    public static Notification create(Long userId, Long bakeryId, UserType userType, TitleType titleType, NotificationType notificationType, String content) {
        return Notification.builder()
                .userId(userId)
                .bakeryId(bakeryId)
                .userType(userType)
                .titleType(titleType)
                .content(content)
                .notificationType(notificationType)
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
