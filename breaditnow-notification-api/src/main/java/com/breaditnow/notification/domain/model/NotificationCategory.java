package com.breaditnow.notification.domain.model;

import com.breaditnow.common.exception.NotificationException;

import static com.breaditnow.common.exception.NotificationErrorCode.INVALID_NOTIFICATION_CATEGORY;

public enum NotificationCategory {
    RESERVATION,
    STOCK,
    SYSTEM;

    public static NotificationCategory from(String text) {
        if(text == null || text.isBlank()) {
            return null;
        }

        for(NotificationCategory category : NotificationCategory.values()) {
            if(category.name().equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new NotificationException(INVALID_NOTIFICATION_CATEGORY);
    }
}
