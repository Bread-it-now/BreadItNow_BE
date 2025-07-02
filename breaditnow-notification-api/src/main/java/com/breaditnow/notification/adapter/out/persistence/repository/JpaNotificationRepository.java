package com.breaditnow.notification.adapter.out.persistence.repository;

import com.breaditnow.notification.adapter.out.persistence.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Page<NotificationEntity> findByBakeryIdOrderByCreatedAtDesc(Long bakeryId, Pageable pageable);
}
