package com.breaditnow.product.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import com.breaditnow.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.product.infrastructure.ProductFavoriteAdapter;
import com.breaditnow.product.presentation.request.ProductFavoriteSearchRequest;
import com.breaditnow.product.presentation.response.ProductFavoritePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class MyProductFavoriteController {
    private final ProductFavoriteAdapter productFavoriteAdapter;

    @GetMapping("/favorite")
    public ApiSuccessResponse<ProductFavoritePageResponse> getFavoriteProducts(@AuthCustomer Long customerId, ProductFavoriteSearchRequest request) {
        ProductFavoriteSearchCriteria productFavoriteSearchCriteria = ProductFavoriteSearchCriteria.of(request);
        return ApiSuccessResponse.of(productFavoriteAdapter.getFavoriteProducts(customerId, productFavoriteSearchCriteria));
    }
}
