package com.breaditnow.customer.domain.notification.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.domain.domain.notification.entity.CustomerNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.breaditnow.domain.domain.notification.repository.UnifiedNotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
	private final UnifiedNotificationRepository unifiedNotificationRepository;

	@Transactional
	public Long readAlertNotification(Long customerId, Long notificationId) {
		updateNotification(customerId, notificationId, notification -> notification.changeIsRead(true));
		return notificationId;
	}

	@Transactional
	public Long deleteNotification(Long customerId, Long notificationId) {
		updateNotification(customerId, notificationId, notification -> notification.changeIsActive(false));
		return notificationId;
	}

	public NotificationPageResponse getNotifications(Long customerId, Pageable pageable,
		List<NotificationType> notificationTypes) {
		return NotificationPageResponse.of(
			unifiedNotificationRepository.find(customerId, pageable, notificationTypes));
	}

	private void updateNotification(Long customerId, Long notificationId, Consumer<CustomerNotification> updater) {
		CustomerNotification notification = unifiedNotificationRepository.getByCustomerIdAndId(customerId,
			notificationId);
		updater.accept(notification);
	}
}
