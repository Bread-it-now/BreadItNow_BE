package com.breaditnow.customer.product.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.product.application.request.HotProductSearchCriteria;
import com.breaditnow.customer.product.infrastructure.ProductAdapter;
import com.breaditnow.customer.product.presentation.response.HotProductPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductAdapter productAdapter;

    @GetMapping("/hot")
    public ApiSuccessResponse<HotProductPageResponse> getHotProducts(@AuthCustomer Long customerId, HotProductSearchRequest request) {
        HotProductSearchCriteria hotProductSearchCriteria = HotProductSearchCriteria.of(request);
        return ApiSuccessResponse.of(productAdapter.getHotProducts(customerId, hotProductSearchCriteria));
    }
}
