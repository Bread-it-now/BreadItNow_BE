package com.breaditnow.notification.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.breaditnow.notification.domain.model.NotificationCategory.RESERVATION;
import static com.breaditnow.notification.domain.model.NotificationCategory.SYSTEM;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    RESERVATION_REQUESTED(RESERVATION),
    RESERVATION_APPROVED(RESERVATION),
    RESERVATION_PARTIALLY_APPROVED(RESERVATION),
    RESERVATION_CANCELED_BY_CUSTOMER(RESERVATION),
    RESERVATION_CANCELED_BY_OWNER(RESERVATION),

    RESERVATION_APPROVAL_FAILED(SYSTEM);

    private final NotificationCategory category;
}
