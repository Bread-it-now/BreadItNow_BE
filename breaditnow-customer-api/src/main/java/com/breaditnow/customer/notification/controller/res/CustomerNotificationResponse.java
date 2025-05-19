package com.breaditnow.customer.notification.controller.res;

import java.time.LocalDateTime;

import com.breaditnow.domain.domain.notification.enumerate.NotificationType;

public interface CustomerNotificationResponse {
	Long notificationId();

	NotificationType type();

	Boolean isRead();

	LocalDateTime createdAt();
}
