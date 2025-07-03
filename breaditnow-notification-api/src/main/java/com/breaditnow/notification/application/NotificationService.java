package com.breaditnow.notification.application;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.domain.Role;
import com.breaditnow.notification.adapter.in.dto.ReservationStatusChangedEvent;
import com.breaditnow.notification.application.provider.NotificationActorProvider;
import com.breaditnow.notification.domain.model.*;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.CUSTOMER;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final FcmNotifier fcmNotifier;
    private final NotificationActorProvider notificationActorProvider;

    @Transactional
    public void processAndSendNotifications(ReservationStatusChangedEvent event) {
        NotificationActor recipient = notificationActorProvider.recipientOf(event.initiatedBy(), event.customerId(), event.ownerId());
        NotificationActor initiator = notificationActorProvider.initiatorOf(event.initiatedBy(), event.customerId(), event.ownerId());

        NotificationType notificationType = mapStatusToNotificationType(event.reservationStatus(), event.initiatedBy());

        NotificationData data = createNotificationData(event);
        NotificationMessage message = notificationType.createMessage(data);
        if (message == null || message.content().isEmpty()) return;

        Notification notification = Notification.create(event.reservationId(), event.bakeryId(), recipient, initiator, notificationType, message.content());
        notificationRepository.save(notification);
        fcmNotifier.notify(recipient, message);
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

    private NotificationData createNotificationData(ReservationStatusChangedEvent event) {
        return NotificationData.builder()
                .customerNickname(event.customerNickname())
                .bakeryName(event.bakeryName())
                .productNames(event.productNames())
                .reservationNumber(event.reservationNumber())
                .pickupDeadline(event.pickupDeadline())
                .cancelReason(event.cancelReason())
                .reservationTime(event.eventOccurredAt())
                .build();
    }
}