package com.breaditnow.customer.domain.productfavorite.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakeryfavorite.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.productfavorite.controller.res.ProductFavoritePageResponse;
import com.breaditnow.customer.domain.productfavorite.service.ProductFavoritePageService;
import com.breaditnow.customer.domain.productfavorite.service.ProductFavoriteService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductFavoriteController {
	private final ProductFavoriteService productFavoriteService;
	private final ProductFavoritePageService productFavoritePageService;

	@PostMapping("/{product_id}/like")
	public ApiSuccessResponse<Map<String, Long>> likeProduct(@AuthCustomer Long customerId,
		@PathVariable("product_id") Long productId) {
		Long savedProductId = productFavoriteService.likeProduct(customerId, productId);
		return ApiSuccessResponse.of("productId", savedProductId);
	}

	@DeleteMapping("/{product_id}/like")
	public ApiSuccessResponse<Map<String, Long>> deleteProduct(@AuthCustomer Long customerId,
		@PathVariable("product_id") Long bakeryId) {
		Long savedProductId = productFavoriteService.deleteProduct(customerId, bakeryId);
		return ApiSuccessResponse.of("productId", savedProductId);
	}

	@GetMapping("/like")
	public ApiSuccessResponse<ProductFavoritePageResponse> getFavorites(@AuthCustomer Long customerId,
		Pageable pageable, @Valid GeoPointRequest geoPointRequest) {
		return ApiSuccessResponse.of(productFavoritePageService.getFavorites(customerId, pageable, geoPointRequest));
	}
}
