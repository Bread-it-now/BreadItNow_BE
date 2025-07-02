package com.breaditnow.notification.adapter.out.persistence;

import com.breaditnow.notification.adapter.out.persistence.entity.NotificationEntity;
import com.breaditnow.notification.adapter.out.persistence.repository.JpaNotificationRepository;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements NotificationRepository {
    private final JpaNotificationRepository jpaNotificationRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = NotificationEntity.from(notification);
        return jpaNotificationRepository.save(entity).toDomain();
    }
}
