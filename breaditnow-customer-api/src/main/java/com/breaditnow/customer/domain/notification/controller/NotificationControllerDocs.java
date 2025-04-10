package com.breaditnow.customer.domain.notification.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 알림 API", description = "알림을 읽음 처리, 삭제 및 목록 조회하는 API입니다.")
public interface NotificationControllerDocs {

	@Operation(summary = "알림 읽음 처리", description = "특정 알림(notification_id)을 '읽음' 상태로 업데이트합니다.")
	@Parameter(name = "notificationId", description = "읽음 처리할 알림의 ID", example = "1001", required = true)
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> readAlertNotification(Long customerId, Long notificationId);

	@Operation(summary = "알림 삭제", description = "특정 알림(notification_id)을 삭제합니다.")
	@Parameter(name = "notificationId", description = "삭제할 알림의 ID", example = "1001", required = true)
	@DomainErrorCodeExamples({NOTIFICATION_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteNotification(Long customerId, Long notificationId);

	@Operation(summary = "알림 목록 조회", description = "받은 알림 목록을 페이지네이션 및 정렬 정보를 통해 조회합니다.")
	@Parameters({
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
		@Parameter(name = "size", description = "한 페이지당 알림 개수", example = "10", in = QUERY),
		@Parameter(name = "sort", description = "정렬 기준", example = "createdAt", in = QUERY),
		@Parameter(name = "type", description = "알림 타입 필터", example = "all", in = QUERY)
	})
	ApiSuccessResponse<NotificationPageResponse> getNotifications(Long customerId, Pageable pageable, String type);
}
