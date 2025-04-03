package com.breaditnow.customer.domain.alert.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.alert.controller.req.CustomerDoNotDisturbUpdateRequest;
import com.breaditnow.customer.domain.alert.controller.res.CustomerDoNotDisturbResponse;
import com.breaditnow.customer.domain.alert.controller.res.CustomerDoNotDisturbToggleResponse;
import com.breaditnow.customer.domain.alert.service.CustomerAlertSettingService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
@Slf4j
public class CustomerAlertSettingController {

    private final CustomerAlertSettingService customerAlertSettingService;

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
}
