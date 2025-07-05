package com.breaditnow.notification.adapter.out.persistence;

import com.breaditnow.notification.adapter.out.persistence.entity.NotificationEntity;
import com.breaditnow.notification.adapter.out.persistence.repository.JpaNotificationRepository;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationCategory;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements NotificationRepository {
    private final JpaNotificationRepository jpaNotificationRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = NotificationEntity.from(notification);
        return jpaNotificationRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Notification> findById(Long notificationId) {
        return jpaNotificationRepository.findById(notificationId)
                .map(NotificationEntity::toDomain);
    }

    @Override
    public Page<Notification> getNotifications(Long bakeryId, NotificationCategory category, Pageable pageable) {
        return jpaNotificationRepository.findNotifications(bakeryId, category, pageable)
                .map(NotificationEntity::toDomain);
    }

}
