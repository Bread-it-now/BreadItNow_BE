package com.breaditnow.notification.domain.port.out;

import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);

    Optional<Notification> findById(Long notificationId);

    Page<Notification> getNotifications(Long bakeryId, NotificationCategory category, Pageable pageable);
}
