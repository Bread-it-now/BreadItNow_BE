package com.breaditnow.customer.common.presentation.swagger.docs;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.request.GlobalAlertSettingUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertSettingResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertSettingToggleResponse;
import com.breaditnow.customer.common.presentation.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 방해금지 설정", description = "고객의 방해금지 설정 관련 API 문서입니다.")
public interface CustomerAlertSettingControllerDocs {

	@Operation(
		summary = "방해 금지 설정 조회",
		description = "고객의 현재 방해 금지 설정 정보를 조회합니다."
	)
	@DomainErrorCodeExamples({ALERT_NOT_FOUND})
	ApiSuccessResponse<GlobalAlertSettingResponse> getDoNotDisturbSetting(Long customerId);

	@Operation(
		summary = "방해 금지 설정 업데이트",
		description = "고객의 방해 금지 설정을 업데이트합니다. 요청 본문에는 업데이트할 설정 정보가 포함됩니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> updateDoNotDisturbSetting(Long customerId, GlobalAlertSettingUpdateRequest request);

	@Operation(
		summary = "방해 금지 설정 토글",
		description = "고객의 방해 금지 상태를 토글(활성화/비활성화)하며, 변경된 상태를 반환합니다."
	)
	@DomainErrorCodeExamples({ALERT_NOT_FOUND})
	ApiSuccessResponse<GlobalAlertSettingToggleResponse> toggleDoNotDisturb(Long customerId);

//	@Operation(
//		summary = "오늘의 알림 목록 조회",
//		description = "고객의 오늘자 알림 목록을 조회합니다."
//	)
//	ApiSuccessResponse<TodayAlertListResponse> getTodayAlerts(Long customerId);
}

