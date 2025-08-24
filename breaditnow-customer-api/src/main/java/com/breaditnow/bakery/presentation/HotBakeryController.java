package com.breaditnow.bakery.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.bakery.application.request.HotBakerySearchCriteria;
import com.breaditnow.bakery.infrastructure.BakeryAdapter;
import com.breaditnow.bakery.presentation.request.HotBakerySearchRequest;
import com.breaditnow.bakery.presentation.response.HotBakeryPageResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bakery/hot")
@RequiredArgsConstructor
public class HotBakeryController {
    private final BakeryAdapter bakeryAdapter;

    @GetMapping
    public ApiSuccessResponse<HotBakeryPageResponse> getHotBakeries(@AuthCustomer Long customerId, HotBakerySearchRequest request) {
        HotBakerySearchCriteria searchCriteria = HotBakerySearchCriteria.of(request);
        return ApiSuccessResponse.of(bakeryAdapter.getHotBakeries(customerId, searchCriteria));
    }
}
