package com.breaditnow.customer.domain.notification.controller;

import static org.springframework.data.domain.Sort.Direction.*;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.customer.domain.notification.service.NotificationPageService;
import com.breaditnow.customer.domain.notification.service.NotificationService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController implements NotificationControllerDocs {
	private final NotificationService notificationService;
	private final NotificationPageService notificationPageService;

	@PatchMapping("/{notificationId}/read")
	public ApiSuccessResponse<Map<String, Long>> readAlertNotification(@AuthCustomer Long customerId,
		@PathVariable("notificationId") Long notificationId) {
		Long savedNotificationId = notificationService.readAlertNotification(customerId, notificationId);
		return ApiSuccessResponse.of("notificationId", savedNotificationId);
	}

	@DeleteMapping("/{notificationId}")
	public ApiSuccessResponse<Map<String, Long>> deleteNotification(@AuthCustomer Long customerId,
		@PathVariable("notificationId") Long notificationId) {
		Long deletedNotificationId = notificationService.deleteNotification(customerId, notificationId);
		return ApiSuccessResponse.of("notificationId", deletedNotificationId);
	}

	@GetMapping()
	public ApiSuccessResponse<NotificationPageResponse> getNotifications(
		@AuthCustomer Long customerId,
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable,
		@RequestParam(value = "type", defaultValue = "all", required = false) String type
	) {
		List<NotificationType> notificationTypes = NotificationType.parseTypes(type);
		return ApiSuccessResponse.of(notificationPageService.getNotifications(customerId, pageable, notificationTypes));
	}
}
