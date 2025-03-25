package com.breaditnow.customer.domain.customer.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.service.CustomerService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/me/info")
    public ApiSuccessResponse<CustomerInfoResponse> getMyInfo(
            @AuthCustomer Long customerId) {
        return ApiSuccessResponse.of(customerService.getCustomerInfo(customerId));
    }
}
