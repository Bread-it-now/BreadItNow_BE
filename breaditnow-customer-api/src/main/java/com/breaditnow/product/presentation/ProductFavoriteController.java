package com.breaditnow.product.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import com.breaditnow.product.application.ProductFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductFavoriteController {
    private final ProductFavoriteService productFavoriteService;

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
}
