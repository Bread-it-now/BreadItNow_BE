package com.breaditnow.customer.domain.notification.controller.res;

import static com.breaditnow.domain.domain.notification.enumerate.NotificationType.*;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record CustomerAlertNotificationResponse(
	Long notificationId,
	NotificationType type,
	Long bakeryId,
	String bakeryName,
	Long productId,
	String productName,
	Integer remainingCount,
	Integer alertCount,
	Boolean isRead,
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime createdAt
) implements CustomerNotificationResponse {
	public static CustomerNotificationResponse of(CustomerAlertNotification alertNotification) {
		return CustomerAlertNotificationResponse.builder()
			.notificationId(alertNotification.getId())
			.type(ALERT)
			.bakeryId(alertNotification.getProduct().getBakery().getId())
			.bakeryName(alertNotification.getBakeryName())
			.productId(alertNotification.getProduct().getId())
			.productName(alertNotification.getProductName())
			.remainingCount(alertNotification.getRemainingCount())
			.alertCount(alertNotification.getAlertCount())
			.isRead(alertNotification.isRead())
			.createdAt(alertNotification.getCreatedAt())
			.build();
	}
}
