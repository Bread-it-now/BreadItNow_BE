package com.breaditnow.customer.product.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.product.application.ProductFavoriteService;
import com.breaditnow.customer.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.customer.product.infrastructure.ProductFavoriteAdapter;
import com.breaditnow.customer.product.presentation.request.ProductFavoriteSearchRequest;
import com.breaditnow.customer.product.presentation.response.ProductFavoritePageDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductFavoriteController {
    private final ProductFavoriteService productFavoriteService;
    private final ProductFavoriteAdapter productFavoriteAdapter;

    @PostMapping("/{productId}/favorite")
    public ApiSuccessResponse<Void> addFavoriteProduct(@AuthCustomer Long customerId, @PathVariable("productId") Long productId) {
        productFavoriteService.addFavoriteProduct(customerId, productId);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{productId}/favorite")
    public ApiSuccessResponse<Void> removeFavoriteProduct(@AuthCustomer Long customerId, @PathVariable("productId") Long productId) {
        productFavoriteService.removeFavoriteProduct(customerId, productId);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/favorite")
    public ApiSuccessResponse<ProductFavoritePageDetailsResponse> getFavoriteProducts(@AuthCustomer Long customerId, ProductFavoriteSearchRequest request) {
        ProductFavoriteSearchCriteria productFavoriteSearchCriteria = ProductFavoriteSearchCriteria.of(request);
        return ApiSuccessResponse.of(productFavoriteAdapter.getFavoriteProducts(customerId, productFavoriteSearchCriteria));
    }
}
