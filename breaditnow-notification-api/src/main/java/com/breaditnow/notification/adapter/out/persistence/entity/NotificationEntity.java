package com.breaditnow.notification.adapter.out.persistence.entity;

import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationType;
import com.breaditnow.notification.domain.model.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String title;
    private String content;
    private boolean isRead;

    public static NotificationEntity from(Notification notification) {
        return NotificationEntity.builder()
                .id(notification.getNotificationId())
                .userId(notification.getUserId())
                .userType(notification.getUserType())
                .notificationType(notification.getNotificationType())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .build();
    }

    public Notification toDomain() {
        return Notification.builder()
                .notificationId(this.id)
                .userId(this.userId)
                .userType(this.userType)
                .notificationType(this.notificationType)
                .content(this.content)
                .isRead(this.isRead)
                .build();
    }
}
