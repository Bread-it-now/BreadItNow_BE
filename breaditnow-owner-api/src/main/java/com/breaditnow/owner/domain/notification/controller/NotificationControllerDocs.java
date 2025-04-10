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

@Tag(name = "Owner - 알림 API", description = "알림 전송 및 관리 기능을 제공하는 API입니다.")
public interface NotificationControllerDocs {

	@Operation(
		summary = "알림 메시지 전송",
		description = "소유자 ID와 알림 요청 데이터를 기반으로 알림 메시지를 전송하고, 전송 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> sendMessage(Long ownerId, NotificationRequest notificationRequest);

	@Operation(
		summary = "알림 읽음 처리",
		description = "소유자 ID와 알림 ID를 기반으로 해당 알림 메시지를 읽음 상태로 처리한 후, 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> readAlertNotification(Long ownerId, Long notificationId);

	@Operation(
		summary = "알림 삭제",
		description = "소유자 ID와 알림 ID를 기반으로 해당 알림 메시지를 삭제하고, 삭제 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteNotification(Long ownerId, Long notificationId);

	@Operation(
		summary = "알림 목록 조회",
		description = "소유자 ID에 해당하는 알림 목록을 페이지 처리하여 조회한 후, 결과를 반환합니다."
	)
	ApiSuccessResponse<NotificationPageResponse> getNotifications(Long ownerId, Pageable pageable);
}
