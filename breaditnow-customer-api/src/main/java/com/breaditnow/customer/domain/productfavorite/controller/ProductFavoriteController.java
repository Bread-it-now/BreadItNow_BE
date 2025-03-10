package com.breaditnow.customer.domain.productfavorite.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.productfavorite.controller.res.ProductFavoritesPageResponse;
import com.breaditnow.customer.domain.productfavorite.service.ProductFavoritePageService;
import com.breaditnow.customer.domain.productfavorite.service.ProductFavoriteService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

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
		Long productFavoriteId = productFavoriteService.likeProduct(customerId, productId);
		return ApiSuccessResponse.of("productFavoriteId", productFavoriteId);
	}

	@DeleteMapping("/{product_id}/like")
	public ApiSuccessResponse<Map<String, Long>> deleteProduct(@AuthCustomer Long customerId,
		@PathVariable("product_id") Long bakeryId) {
		Long productFavoriteId = productFavoriteService.deleteProduct(customerId, bakeryId);
		return ApiSuccessResponse.of("productFavoriteId", productFavoriteId);
	}

	@GetMapping("/like")
	public ApiSuccessResponse<ProductFavoritesPageResponse> getFavorites(@AuthCustomer Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "LATEST") String sortType) {
		return ApiSuccessResponse.of(productFavoritePageService.getFavorites(customerId, page, size, sortType));
	}
}
