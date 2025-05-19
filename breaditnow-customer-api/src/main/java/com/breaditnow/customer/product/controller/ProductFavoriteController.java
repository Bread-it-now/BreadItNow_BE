package com.breaditnow.customer.product.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.common.presentation.swagger.docs.ProductFavoriteControllerDocs;
import com.breaditnow.customer.product.controller.res.ProductFavoritePageResponse;
import com.breaditnow.customer.product.service.ProductFavoritePageService;
import com.breaditnow.customer.product.service.ProductFavoriteService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductFavoriteController implements ProductFavoriteControllerDocs {
    private final ProductFavoriteService productFavoriteService;
    private final ProductFavoritePageService productFavoritePageService;

    @PostMapping("/{productId}/favorite")
    public ApiSuccessResponse<Map<String, Long>> likeProduct(@AuthCustomer Long customerId,
                                                             @PathVariable("productId") Long productId) {
        Long savedProductId = productFavoriteService.likeProduct(customerId, productId);
        return ApiSuccessResponse.of("productId", savedProductId);
    }

    @DeleteMapping("/{productId}/favorite")
    public ApiSuccessResponse<Map<String, Long>> deleteProduct(@AuthCustomer Long customerId,
                                                               @PathVariable("productId") Long bakeryId) {
        Long savedProductId = productFavoriteService.deleteProduct(customerId, bakeryId);
        return ApiSuccessResponse.of("productId", savedProductId);
    }

    @GetMapping("/favorite")
    public ApiSuccessResponse<ProductFavoritePageResponse> getFavorites(@AuthCustomer Long customerId,
                                                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                                        @RequestParam(name = "sort", defaultValue = "latest") String sort,
                                                                        @Valid GeoPointRequest geoPointRequest) {
        return ApiSuccessResponse.of(
                productFavoritePageService.getFavorites(customerId, page, size, sort, geoPointRequest));
    }
}
