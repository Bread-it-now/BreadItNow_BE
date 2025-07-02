package com.breaditnow.notification.domain.port.out;

import com.breaditnow.notification.domain.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepository {
    Notification save(Notification notification);
    Page<Notification> getNotifications(Long bakeryId, Pageable pageable);
}
