package com.breaditnow.customer.domain.customer.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.customer.controller.req.CustomerInitRequest;
import com.breaditnow.customer.domain.customer.controller.req.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.controller.res.NicknameDuplicateResponse;
import com.breaditnow.customer.domain.customer.service.CustomerService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/me/init")
    public ApiSuccessResponse<Void> initCustomerInfo(
            @AuthCustomer Long customerId,
            @RequestBody @Valid CustomerInitRequest request) {

        customerService.initCustomerInfo(customerId, request);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/me/info")
    public ApiSuccessResponse<CustomerInfoResponse> getMyInfo(
            @AuthCustomer Long customerId) {
        return ApiSuccessResponse.of(customerService.getCustomerInfo(customerId));
    }

    @GetMapping("/duplicate-nickname")
    public ApiSuccessResponse<NicknameDuplicateResponse> checkDuplicateNickname(
            @RequestParam("nickname") String nickname) {
        return ApiSuccessResponse.of(customerService.checkDuplicateNickname(nickname));
    }

    @PatchMapping("/me/region")
    public ApiSuccessResponse<Void> updateMyRegions(
            @AuthCustomer Long customerId,
            @RequestBody @Valid RegionUpdateRequest request) {

        customerService.updateCustomerRegions(customerId, request);
        return ApiSuccessResponse.of();
    }
}
