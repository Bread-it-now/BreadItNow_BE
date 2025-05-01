package com.breaditnow.domain.domain.notification.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.notification.entity.CustomerNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;

public interface UnifiedNotificationRepositoryCustom {
	Page<CustomerNotification> find(Long customerId, Pageable pageable, List<NotificationType> notificationTypes);
}
