package com.breaditnow.customer.bakery.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.bakery.infrastructure.BakeryAdapter;
import com.breaditnow.customer.bakery.presentation.response.BakeryDetailResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bakery")
@RequiredArgsConstructor
public class BakeryController {
    private final BakeryAdapter bakeryAdapter;

    @GetMapping("/{bakery_id}/detail")
    public ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(@AuthCustomer Long customerId, @PathVariable("bakery_id") Long bakeryId) {
        return ApiSuccessResponse.of(bakeryAdapter.getBakeryDetail(customerId, bakeryId));
    }
}
