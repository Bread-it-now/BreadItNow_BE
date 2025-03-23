package com.breaditnow.owner.domain.notification.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;
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
	private final NotificationService notificationService;

	// 갓 나온 빵 알림 전송
	@PostMapping()
	public ApiSuccessResponse<Map<String, Long>> sendMessage(@AuthOwner Long ownerId,
		@RequestBody @Valid NotificationRequest notificationRequest) {
		notificationService.sendNotification(ownerId, notificationRequest);
		return ApiSuccessResponse.of();
	}

}
