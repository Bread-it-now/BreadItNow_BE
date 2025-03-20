package com.breaditnow.customer.domain.alert.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.alert.service.CustomerProductAlertService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
@Slf4j
public class CustomerProductAlertController {

    private final CustomerProductAlertService customerProductAlertService;

    @PostMapping("/product/{product_id}")
    public ApiSuccessResponse<Map<String, Long>> registerProductAlert(
            @AuthCustomer Long customerId,
            @PathVariable("product_id") Long productId) {
        return customerProductAlertService.registerProductAlert(customerId, productId);
    }
}
