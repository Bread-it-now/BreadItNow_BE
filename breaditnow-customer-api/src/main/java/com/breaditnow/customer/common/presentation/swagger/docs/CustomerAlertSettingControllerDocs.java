package com.breaditnow.customer.common.presentation.swagger.docs;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertToggleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 방해금지 설정", description = "고객의 방해금지 설정 관련 API 문서입니다.")
public interface CustomerAlertSettingControllerDocs {

	@Operation(
		summary = "방해 금지 설정 조회",
		description = "고객의 현재 방해 금지 설정 정보를 조회합니다."
	)
	ApiSuccessResponse<GlobalAlertResponse> getDoNotDisturbSetting(Long customerId);

	@Operation(
		summary = "방해 금지 설정 업데이트",
		description = "고객의 방해 금지 설정을 업데이트합니다. 요청 본문에는 업데이트할 설정 정보가 포함됩니다."
	)
	ApiSuccessResponse<Void> updateDoNotDisturbSetting(Long customerId, GlobalAlertUpdateRequest request);

	@Operation(
		summary = "방해 금지 설정 토글",
		description = "고객의 방해 금지 상태를 토글(활성화/비활성화)하며, 변경된 상태를 반환합니다."
	)
	ApiSuccessResponse<GlobalAlertToggleResponse> toggleDoNotDisturb(Long customerId);

//	@Operation(
//		summary = "오늘의 알림 목록 조회",
//		description = "고객의 오늘자 알림 목록을 조회합니다."
//	)
//	ApiSuccessResponse<TodayAlertListResponse> getTodayAlerts(Long customerId);
}

