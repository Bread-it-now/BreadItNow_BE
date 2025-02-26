package com.breaditnow.domain.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.notification.entity.CustomerNotification;

public interface CustomerNotificationRepository extends JpaRepository<CustomerNotification, Long> {

}
