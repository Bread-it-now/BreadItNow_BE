package com.breaditnow.customer.domain.notification.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.notification.service.AlertNotificationService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert-notification")
public class AlertNotificationController {
	private final AlertNotificationService alertNotificationService;

	@PatchMapping("/{alert_notification_id}/read")
	public ApiSuccessResponse<Map<String, Long>> readAlertNotification(@AuthCustomer Long customerId,
		@PathVariable("alert_notification_id") Long notificationId) {

		Long alertNotificationId = alertNotificationService.readAlertNotification(customerId, notificationId);
		return ApiSuccessResponse.of("alertNotificationId", alertNotificationId);
	}

	@DeleteMapping("/{alert_notification_id}")
	public ApiSuccessResponse<Map<String, Long>> deleteNotification(@AuthCustomer Long customerId,
		@PathVariable("alert_notification_id") Long notificationId) {

		Long alertNotificationId = alertNotificationService.deleteNotification(customerId, notificationId);
		return ApiSuccessResponse.of("alertNotificationId", alertNotificationId);
	}
}
