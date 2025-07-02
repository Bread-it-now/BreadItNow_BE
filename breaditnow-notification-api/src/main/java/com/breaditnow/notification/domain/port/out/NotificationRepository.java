package com.breaditnow.notification.domain.port.out;

import com.breaditnow.notification.domain.model.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);
}
