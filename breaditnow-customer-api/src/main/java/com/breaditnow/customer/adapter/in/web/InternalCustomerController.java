package com.breaditnow.customer.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.application.CustomerService;
import com.breaditnow.customer.application.dto.internal.CustomerInfo;
import com.breaditnow.customer.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/customer")
@RequiredArgsConstructor
public class InternalCustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}/fcm-token")
    public ApiSuccessResponse<String> getOwnerFcmToken(@PathVariable Long customerId) {
        Customer customer = customerService.loadCustomer(customerId);
        return ApiSuccessResponse.of(customer.getFcmToken());
    }

    @GetMapping("/{customerId}")
    public ApiSuccessResponse<CustomerInfo> getCustomerInfo(@PathVariable Long customerId) {
        Customer customer = customerService.loadCustomer(customerId);
        return ApiSuccessResponse.of(CustomerInfo.of(customer));
    }
}
