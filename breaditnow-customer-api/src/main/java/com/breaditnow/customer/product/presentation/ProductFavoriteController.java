package com.breaditnow.customer.product.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.product.application.ProductFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductFavoriteController {
    private final ProductFavoriteService productFavoriteService;

    @PostMapping("/{productId}/favorite")
    public ApiSuccessResponse<Void> likeProduct(@AuthCustomer Long customerId, @PathVariable("productId") Long productId) {
        productFavoriteService.addFavoriteProduct(customerId, productId);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{productId}/favorite")
    public ApiSuccessResponse<Void> deleteProduct(@AuthCustomer Long customerId, @PathVariable("productId") Long productId) {
        productFavoriteService.removeFavoriteProduct(customerId, productId);
        return ApiSuccessResponse.of();
    }
}
