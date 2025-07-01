package com.breaditnow.customer.customer.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.customer.application.internal.CustomerInfo;
import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.customer.domain.port.in.GetCustomerInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/customer")
@RequiredArgsConstructor
public class InternalCustomerController {
    private final GetCustomerInfoUseCase getCustomerInfoUseCase;

    @GetMapping("/{customerId}/fcm-token")
    public ApiSuccessResponse<String> getOwnerFcmToken(@PathVariable Long customerId) {
        String fcmToken = getCustomerInfoUseCase.findById(customerId)
                .map(Customer::getFcmToken)
                .orElse(null);

        return ApiSuccessResponse.of(fcmToken);
    }

    @GetMapping("/{customerId}")
    public ApiSuccessResponse<CustomerInfo> getCustomerInfo(@PathVariable Long customerId) {
        CustomerInfo customerInfo = getCustomerInfoUseCase.findById(customerId)
                .map(CustomerInfo::of)
                .orElse(null);

        return ApiSuccessResponse.of(customerInfo);
    }
}
