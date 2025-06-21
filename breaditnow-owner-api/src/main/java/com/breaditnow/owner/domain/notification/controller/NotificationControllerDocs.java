package com.breaditnow.owner.domain.notification.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;
import com.breaditnow.owner.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.owner.common.presentation.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 알림 API", description = "알림 전송 및 관리 기능을 제공하는 API입니다.")
public interface NotificationControllerDocs {

	@Operation(summary = "알림 전송", description = "알림 요청 정보를 받아, 다른 기기에 푸시 메시지를 전송합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> sendMessage(Long ownerId, NotificationRequest notificationRequest);

	@Operation(summary = "알림 읽음 처리", description = "특정 알림(notificationId)에 대해 '읽음' 상태로 업데이트합니다.")
	@Parameter(name = "notificationId", description = "읽음 처리할 알림의 ID", example = "10", required = true)
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> readAlertNotification(Long ownerId, Long notificationId);

	@Operation(summary = "알림 삭제", description = "특정 알림(notificationId)을 삭제합니다.")
	@Parameter(name = "notificationId", description = "삭제할 알림의 ID", example = "10", required = true)
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteNotification(Long ownerId, Long notificationId);

	@Operation(summary = "알림 목록 조회", description = "알림 목록을 조회합니다. 기본 정렬 기준(createdAt DESC)으로 페이지네이션이 적용되어 있습니다.")
	ApiSuccessResponse<NotificationPageResponse> getNotifications(Long ownerId, Pageable pageable);
}
