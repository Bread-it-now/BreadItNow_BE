package com.breaditnow.owner.domain.notification.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;
import com.breaditnow.owner.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 알림 API")
public interface NotificationControllerDocs {
	@Operation(summary = "소유자 ID와 알림 요청 정보를 기반으로 알림 메시지를 전송합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> sendMessage(Long ownerId, NotificationRequest notificationRequest);

	@Operation(summary = "소유자 ID와 알림 ID를 기반으로 알림 메시지를 읽음 처리합니다.")
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> readAlertNotification(Long ownerId, Long notificationId);

	@Operation(summary = "소유자 ID와 알림 ID를 기반으로 알림 메시지를 삭제합니다.")
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteNotification(Long ownerId, Long notificationId);

	@Operation(summary = "소유자 ID에 해당하는 알림 목록을 페이징 처리하여 조회합니다.")
	ApiSuccessResponse<NotificationPageResponse> getNotifications(Long ownerId, Pageable pageable);
}
