package com.breaditnow.customer.customer.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.customer.application.port.in.GetCustomerInfoListUseCase;
import com.breaditnow.customer.customer.application.response.CustomerProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/v1/customer")
@RequiredArgsConstructor
public class CustomerInternalController {
    private final GetCustomerInfoListUseCase getCustomerInfoListUseCase;

    @GetMapping("/profile")
    public ApiSuccessResponse<List<CustomerProfileResponse>> getCustomerInfos(@RequestParam("ids") List<Long> customerIds) {
        return ApiSuccessResponse.of(getCustomerInfoListUseCase.getCustomerInfoList(customerIds));
    }
}
