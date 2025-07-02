package com.breaditnow.notification.adapter.out.persistence.entity;

import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationType;
import com.breaditnow.notification.domain.model.TitleType;
import com.breaditnow.notification.domain.model.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

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
    private Long bakeryId;

    @Enumerated(STRING)
    private UserType userType;

    @Enumerated(STRING)
    private NotificationType notificationType;

    @Enumerated(STRING)
    private TitleType titleType;

    private String content;
    private boolean isRead;

    public static NotificationEntity from(Notification notification) {
        return NotificationEntity.builder()
                .id(notification.getNotificationId())
                .userId(notification.getUserId())
                .bakeryId(notification.getBakeryId())
                .userType(notification.getUserType())
                .notificationType(notification.getNotificationType())
                .titleType(notification.getTitleType())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .build();
    }

    public Notification toDomain() {
        return Notification.builder()
                .notificationId(this.id)
                .userId(this.userId)
                .userType(this.userType)
                .bakeryId(this.bakeryId)
                .titleType(this.titleType)
                .content(this.content)
                .notificationType(this.notificationType)
                .isRead(this.isRead)
                .build();
    }
}
