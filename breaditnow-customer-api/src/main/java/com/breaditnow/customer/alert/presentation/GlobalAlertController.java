package com.breaditnow.customer.alert.presentation;

import com.breaditnow.customer.common.presentation.swagger.docs.CustomerAlertSettingControllerDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertToggleResponse;
import com.breaditnow.customer.alert.application.GlobalAlertService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class GlobalAlertController implements CustomerAlertSettingControllerDocs {
	private final GlobalAlertService globalAlertService;
//	private final CustomerProductAlertService customerProductAlertService;

	@GetMapping("/do-not-disturb")
	public ApiSuccessResponse<GlobalAlertResponse> getDoNotDisturbSetting(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(globalAlertService.getDoNotDisturbSetting(customerId));
	}

	@PutMapping("/do-not-disturb")
	public ApiSuccessResponse<Void> updateDoNotDisturbSetting(@AuthCustomer Long customerId, @RequestBody GlobalAlertUpdateRequest dto) {
		globalAlertService.updateDoNotDisturbSetting(customerId, dto);
		return ApiSuccessResponse.of();
	}

	@PatchMapping("/do-not-disturb/toggle")
	public ApiSuccessResponse<GlobalAlertToggleResponse> toggleDoNotDisturb(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(globalAlertService.toggleSettings(customerId));
	}

//	@GetMapping("/today")
//	public ApiSuccessResponse<TodayAlertListResponse> getTodayAlerts(@AuthCustomer Long customerId) {
//		TodayAlertListResponse response = customerProductAlertService.getTodayAlerts(customerId);
//		return ApiSuccessResponse.of(response);
//	}
}
