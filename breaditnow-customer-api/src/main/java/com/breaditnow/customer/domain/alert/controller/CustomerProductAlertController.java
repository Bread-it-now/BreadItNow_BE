package com.breaditnow.customer.domain.alert.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.alert.controller.res.CustomerProductAlertPageResponse;
import com.breaditnow.customer.domain.alert.service.CustomerProductAlertService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/product/{product_id}")
    public ApiSuccessResponse<Void> deleteProductAlert(
            @AuthCustomer Long customerId,
            @PathVariable("product_id") Long productId) {
        customerProductAlertService.deactivateProductAlert(customerId, productId);
        return ApiSuccessResponse.of(null);
    }

    @PatchMapping("/product/{product_id}/toggle")
    public ApiSuccessResponse<Map<String, Boolean>> toggleProductAlert(
            @AuthCustomer Long customerId,
            @PathVariable("product_id") Long productId) {
        boolean newStatus = customerProductAlertService.toggleProductAlert(customerId, productId);
        return ApiSuccessResponse.of("active", newStatus);
    }

    @GetMapping("/product")
    public ApiSuccessResponse<CustomerProductAlertPageResponse> getProductAlerts(
            @AuthCustomer Long customerId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.of(customerProductAlertService.getProductAlerts(customerId, page, size));
    }
}
