package com.breaditnow.product.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import com.breaditnow.product.application.request.HotProductSearchCriteria;
import com.breaditnow.product.infrastructure.ProductAdapter;
import com.breaditnow.product.presentation.request.HotProductSearchRequest;
import com.breaditnow.product.presentation.response.HotProductPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/hot")
public class HotProductController {
    private final ProductAdapter productAdapter;

    @GetMapping
    public ApiSuccessResponse<HotProductPageResponse> getHotProducts(@AuthCustomer Long customerId, HotProductSearchRequest request) {
        HotProductSearchCriteria hotProductSearchCriteria = HotProductSearchCriteria.of(request);
        return ApiSuccessResponse.of(productAdapter.getHotProducts(customerId, hotProductSearchCriteria));
    }
}
