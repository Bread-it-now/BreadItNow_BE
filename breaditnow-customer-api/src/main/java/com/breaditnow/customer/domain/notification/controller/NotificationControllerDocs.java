package com.breaditnow.customer.domain.notification.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 알림 API")
public interface NotificationControllerDocs {
	@Operation(summary = "고객 ID와 알림 ID를 기반으로 알림을 읽음 처리합니다.")
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> readAlertNotification(Long customerId, Long notificationId);

	@Operation(summary = "고객 ID와 알림 ID를 기반으로 알림을 삭제합니다.")
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteNotification(Long customerId, Long notificationId);

	@Operation(summary = "고객 ID, 페이징 정보, 및 타입을 기반으로 알림 목록을 조회합니다.")
	ApiSuccessResponse<NotificationPageResponse> getNotifications(Long customerId, Pageable pageable, String type);
}
