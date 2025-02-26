package com.breaditnow.domain.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.notification.entity.OwnerNotification;

public interface OwnerNotificationRepository extends JpaRepository<OwnerNotification, Long> {

}

