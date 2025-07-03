package com.breaditnow.notification.application;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.domain.Role;
import com.breaditnow.notification.adapter.in.dto.ReservationStatusChangedEvent;
import com.breaditnow.notification.domain.model.*;
import com.breaditnow.notification.domain.port.out.CustomerApiPort;
import com.breaditnow.notification.domain.port.out.FcmPort;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import com.breaditnow.notification.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmPort fcmPort;
    private final NotificationRepository notificationRepository;
    private final OwnerApiPort ownerApiPort;
    private final CustomerApiPort customerApiPort;

    @Transactional
    public void processAndSendNotifications(ReservationStatusChangedEvent event) {
        Role initiatedBy = event.initiatedBy();
        NotificationType notificationType = mapStatusToNotificationType(event.reservationStatus(), initiatedBy);

        NotificationActor initiator = new NotificationActor(
                initiatedBy == CUSTOMER ? event.customerId() : event.ownerId(),
                initiatedBy
        );
        NotificationActor recipient = new NotificationActor(
                initiatedBy == CUSTOMER ? event.ownerId() : event.customerId(),
                initiatedBy == CUSTOMER ? OWNER : CUSTOMER
        );

        NotificationData data = NotificationData.builder()
                .customerNickname(event.customerNickname())
                .bakeryName(event.bakeryName())
                .productNames(event.productNames())
                .pickupDeadline(event.pickupDeadline())
                .cancelReason(event.cancelReason())
                .reservationTime(event.eventOccurredAt())
                .build();

        NotificationMessage message = notificationType.createMessage(data);
        if (message == null || message.content().isEmpty()) return;

        saveNotification(event, recipient, initiator, notificationType, message);
        sendFcm(recipient, message);
    }

    private void saveNotification(ReservationStatusChangedEvent event, NotificationActor recipient, NotificationActor initiator, NotificationType notificationType, NotificationMessage message) {
        Notification notification = Notification.create(
                event.reservationId(),
                event.bakeryId(),
                recipient,
                initiator,
                notificationType,
                message.content()
        );
        notificationRepository.save(notification);
    }

    private void sendFcm(NotificationActor recipient, NotificationMessage message) {
        if (recipient.role() == OWNER) {
            ownerApiPort.findFcmTokenById(recipient.userId())
                    .ifPresent(token -> fcmPort.sendNotification(token, message.title(), message.content()));
        } else if (recipient.role() == CUSTOMER) {
            customerApiPort.findFcmTokenById(recipient.userId())
                    .ifPresent(token -> fcmPort.sendNotification(token, message.title(), message.content()));
        }
    }

    private NotificationType mapStatusToNotificationType(ReservationStatus status, Role role) {
        return switch (status) {
            case WAITING -> NotificationType.RESERVATION_REQUESTED;
            case APPROVED -> NotificationType.RESERVATION_APPROVED;
            case PARTIAL_APPROVED -> NotificationType.RESERVATION_PARTIALLY_APPROVED;
            case CANCELLED -> {
                if (role == CUSTOMER) {
                    yield NotificationType.RESERVATION_CANCELED_BY_CUSTOMER;
                } else {
                    yield NotificationType.RESERVATION_CANCELED_BY_OWNER;
                }
            }
        };
    }
}