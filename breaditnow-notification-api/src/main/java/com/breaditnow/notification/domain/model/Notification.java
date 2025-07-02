package com.breaditnow.notification.domain.model;

import lombok.Builder;
import lombok.Getter;

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

    @Builder
    public Notification(Long notificationId, Long userId, Long bakeryId, UserType userType, TitleType titleType, NotificationType notificationType, String content, boolean isRead) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.bakeryId = bakeryId;
        this.userType = userType;
        this.titleType = titleType;
        this.notificationType = notificationType;
        this.content = content;
        this.isRead = isRead;
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
                .build();
    }
}
