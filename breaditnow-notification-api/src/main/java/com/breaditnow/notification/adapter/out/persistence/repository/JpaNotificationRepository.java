package com.breaditnow.notification.adapter.out.persistence.repository;

import com.breaditnow.notification.adapter.out.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
