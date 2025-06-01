package com.breaditnow.customer.alert.presentation;

import com.breaditnow.customer.common.presentation.swagger.docs.CustomerAlertSettingControllerDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.request.GlobalAlertSettingUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertSettingResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertSettingToggleResponse;
import com.breaditnow.customer.alert.application.GlobalAlertSettingService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class GlobalAlertSettingController implements CustomerAlertSettingControllerDocs {
	private final GlobalAlertSettingService globalAlertSettingService;
//	private final CustomerProductAlertService customerProductAlertService;

	@GetMapping("/do-not-disturb")
	public ApiSuccessResponse<GlobalAlertSettingResponse> getDoNotDisturbSetting(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(globalAlertSettingService.getDoNotDisturbSetting(customerId));
	}

	@PutMapping("/do-not-disturb")
	public ApiSuccessResponse<Void> updateDoNotDisturbSetting(@AuthCustomer Long customerId, @RequestBody GlobalAlertSettingUpdateRequest dto) {
		globalAlertSettingService.updateDoNotDisturbSetting(customerId, dto);
		return ApiSuccessResponse.of();
	}

	@PatchMapping("/do-not-disturb/toggle")
	public ApiSuccessResponse<GlobalAlertSettingToggleResponse> toggleDoNotDisturb(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(globalAlertSettingService.toggleSettings(customerId));
	}

//	@GetMapping("/today")
//	public ApiSuccessResponse<TodayAlertListResponse> getTodayAlerts(@AuthCustomer Long customerId) {
//		TodayAlertListResponse response = customerProductAlertService.getTodayAlerts(customerId);
//		return ApiSuccessResponse.of(response);
//	}
}
