package com.breaditnow.customer.alert.presentation;

import com.breaditnow.customer.common.presentation.swagger.docs.CustomerAlertSettingControllerDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.request.CustomerDoNotDisturbUpdateRequest;
import com.breaditnow.customer.alert.application.response.CustomerDoNotDisturbResponse;
import com.breaditnow.customer.alert.application.response.CustomerDoNotDisturbToggleResponse;
import com.breaditnow.customer.alert.application.response.TodayAlertListResponse;
import com.breaditnow.customer.alert.application.CustomerAlertSettingService;
import com.breaditnow.customer.alert.application.CustomerProductAlertService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
@Slf4j
public class CustomerAlertSettingController implements CustomerAlertSettingControllerDocs {

	private final CustomerAlertSettingService customerAlertSettingService;
	private final CustomerProductAlertService customerProductAlertService;

	@GetMapping("/do-not-disturb")
	public ApiSuccessResponse<CustomerDoNotDisturbResponse> getDoNotDisturbSetting(
		@AuthCustomer Long customerId
	) {
		return ApiSuccessResponse.of(customerAlertSettingService.getDoNotDisturbSetting(customerId));
	}

	@PutMapping("/do-not-disturb")
	public ApiSuccessResponse<Void> updateDoNotDisturbSetting(
		@AuthCustomer Long customerId,
		@RequestBody CustomerDoNotDisturbUpdateRequest request
	) {
		customerAlertSettingService.updateDoNotDisturbSetting(customerId, request);
		return ApiSuccessResponse.of();
	}

	@PatchMapping("/do-not-disturb/toggle")
	public ApiSuccessResponse<CustomerDoNotDisturbToggleResponse> toggleDoNotDisturb(
		@AuthCustomer Long customerId
	) {
		boolean isActive = customerAlertSettingService.toggleDoNotDisturb(customerId);
		return ApiSuccessResponse.of(CustomerDoNotDisturbToggleResponse.of(isActive));
	}

	@GetMapping("/today")
	public ApiSuccessResponse<TodayAlertListResponse> getTodayAlerts(
		@AuthCustomer Long customerId) {
		TodayAlertListResponse response = customerProductAlertService.getTodayAlerts(customerId);
		return ApiSuccessResponse.of(response);
	}
}
