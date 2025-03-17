package com.breaditnow.domain.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;

public interface CustomerAlertNotificationRepository extends JpaRepository<CustomerAlertNotification, Long> {
}
