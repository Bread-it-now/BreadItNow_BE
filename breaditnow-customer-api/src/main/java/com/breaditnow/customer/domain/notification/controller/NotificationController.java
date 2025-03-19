package com.breaditnow.customer.domain.notification.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.customer.domain.notification.service.NotificationService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert-notification")
public class NotificationController {
	private final NotificationService notificationService;

	@PatchMapping("/{alert_notification_id}/read")
	public ApiSuccessResponse<Map<String, Long>> readAlertNotification(@AuthCustomer Long customerId,
		@PathVariable("alert_notification_id") Long notificationId, @RequestParam("type") String type) {

		List<NotificationType> notificationTypes = NotificationType.parseTypes(type);
		Long alertNotificationId = notificationService.readAlertNotification(customerId, notificationId,
			notificationTypes);
		return ApiSuccessResponse.of("alertNotificationId", alertNotificationId);
	}

	@DeleteMapping("/{alert_notification_id}")
	public ApiSuccessResponse<Map<String, Long>> deleteNotification(@AuthCustomer Long customerId,
		@PathVariable("alert_notification_id") Long notificationId, @RequestParam("type") String type) {

		List<NotificationType> notificationTypes = NotificationType.parseTypes(type);
		Long alertNotificationId = notificationService.deleteNotification(customerId, notificationId,
			notificationTypes);
		return ApiSuccessResponse.of("alertNotificationId", alertNotificationId);
	}

	@GetMapping()
	public ApiSuccessResponse<NotificationPageResponse> getNotifications(@AuthCustomer Long customerId,
		Pageable pageable, @RequestParam("type") String type) {
		List<NotificationType> notificationTypes = NotificationType.parseTypes(type);
		return ApiSuccessResponse.of(notificationService.getNotifications(customerId, pageable, notificationTypes));
	}
}
