package com.breaditnow.customer.domain.notification.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.breaditnow.domain.domain.notification.repository.UnifiedNotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationPageService {
	private final UnifiedNotificationRepository unifiedNotificationRepository;

	public NotificationPageResponse getNotifications(Long customerId, Pageable pageable,
		List<NotificationType> notificationTypes) {
		return NotificationPageResponse.of(
			unifiedNotificationRepository.find(customerId, pageable, notificationTypes));
	}
}
