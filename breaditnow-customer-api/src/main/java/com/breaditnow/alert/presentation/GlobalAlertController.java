package com.breaditnow.alert.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.alert.application.GlobalAlertService;
import com.breaditnow.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.alert.application.response.GlobalAlertResponse;
import com.breaditnow.alert.application.response.GlobalAlertToggleResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class GlobalAlertController {
	private final GlobalAlertService globalAlertService;

	@GetMapping("/do-not-disturb")
	public ApiSuccessResponse<GlobalAlertResponse> getDoNotDisturbSetting(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(globalAlertService.getDoNotDisturbSetting(customerId));
	}

	@PutMapping("/do-not-disturb")
	public ApiSuccessResponse<Void> updateDoNotDisturbSetting(@AuthCustomer Long customerId, @RequestBody GlobalAlertUpdateRequest request) {
		globalAlertService.updateDoNotDisturbSetting(customerId, request);
		return ApiSuccessResponse.of();
	}

	@PatchMapping("/do-not-disturb/toggle")
	public ApiSuccessResponse<GlobalAlertToggleResponse> toggleDoNotDisturb(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(globalAlertService.toggleSettings(customerId));
	}
}
