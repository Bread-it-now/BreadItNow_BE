package com.breaditnow.owner.domain.notification.controller;

import static org.springframework.data.domain.Sort.Direction.*;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;
import com.breaditnow.owner.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.owner.domain.notification.service.NotificationSendService;
import com.breaditnow.owner.domain.notification.service.NotificationService;
import com.breaditnow.owner.global.security.annotation.AuthOwner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
	private final NotificationSendService notificationSendService;
	private final NotificationService notificationService;

	@PostMapping()
	public ApiSuccessResponse<Map<String, Long>> sendMessage(@AuthOwner Long ownerId,
		@RequestBody @Valid NotificationRequest notificationRequest) {
		notificationSendService.sendNotification(ownerId, notificationRequest);
		return ApiSuccessResponse.of();
	}

	@GetMapping()
	public ApiSuccessResponse<NotificationPageResponse> getNotifications(
		@AuthOwner Long ownerId,
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
		return ApiSuccessResponse.of(notificationService.getNotifications(ownerId, pageable));
	}

}
