package com.breaditnow.notification.adapter.out.persistence.entity;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationCategory;
import com.breaditnow.notification.domain.model.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "notification")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
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
    private NotificationCategory notificationCategory;

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
                .recipientType(notification.getRecipient().type())
                .recipientId(notification.getRecipient().id())
                .notificationCategory(notification.getNotificationCategory())
                .initiatorType(notification.getInitiator().type())
                .initiatorId(notification.getInitiator().id())
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
                .recipient(new UserIdentifier(this.recipientId, this.recipientType))
                .initiator(new UserIdentifier(this.initiatorId, this.initiatorType))
                .bakeryId(this.bakeryId)
                .content(this.content)
                .notificationCategory(this.notificationCategory)
                .notificationType(this.notificationType)
                .isRead(this.isRead)
                .isDeleted(this.isDeleted)
                .createdAt(this.getCreatedAt())
                .build();
    }
}
