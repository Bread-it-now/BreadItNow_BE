package com.breaditnow.notification.adapter.out.persistence.repository;

import com.breaditnow.notification.adapter.out.persistence.entity.NotificationEntity;
import com.breaditnow.notification.domain.model.NotificationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query(value = "SELECT n FROM NotificationEntity n WHERE n.bakeryId = :bakeryId AND (:category IS NULL OR n.notificationCategory = :category)",
            countQuery = "SELECT count(n) FROM NotificationEntity n WHERE n.bakeryId = :bakeryId AND (:category IS NULL OR n.notificationCategory = :category)")
    Page<NotificationEntity> findNotifications(@Param("bakeryId") Long bakeryId, @Param("category") NotificationCategory category, Pageable pageable);
}
