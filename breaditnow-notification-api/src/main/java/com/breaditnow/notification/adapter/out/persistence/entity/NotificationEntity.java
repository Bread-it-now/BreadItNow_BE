package com.breaditnow.notification.adapter.out.persistence.entity;

import com.breaditnow.common.domain.Role;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationActor;
import com.breaditnow.notification.domain.model.NotificationType;
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
    private Long reservationId;
    private Long bakeryId;

    @Enumerated(STRING)
    private Role recipientType;
    private Long recipientId;

    @Enumerated(STRING)
    private Role initiatorType;
    private Long initiatorId;

    @Enumerated(STRING)
    private NotificationType notificationType;

    private String content;
    private boolean isRead;
    private boolean isDeleted;

    public static NotificationEntity from(Notification notification) {
        return NotificationEntity.builder()
                .id(notification.getNotificationId())
                .reservationId(notification.getReservationId())
                .bakeryId(notification.getBakeryId())
                .recipientType(notification.getRecipient().role())
                .recipientId(notification.getRecipient().userId())
                .initiatorType(notification.getInitiator().role())
                .initiatorId(notification.getInitiator().userId())
                .notificationType(notification.getNotificationType())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .isDeleted(notification.isDeleted())
                .build();
    }

    public Notification toDomain() {
        return Notification.builder()
                .notificationId(this.id)
                .reservationId(this.reservationId)
                .recipient(new NotificationActor(this.recipientId, this.recipientType))
                .initiator(new NotificationActor(this.initiatorId, this.initiatorType))
                .bakeryId(this.bakeryId)
                .content(this.content)
                .notificationType(this.notificationType)
                .isRead(this.isRead)
                .isDeleted(this.isDeleted)
                .build();
    }
}
