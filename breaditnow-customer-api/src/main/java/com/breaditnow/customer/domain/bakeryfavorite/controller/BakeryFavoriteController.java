package com.breaditnow.customer.domain.bakeryfavorite.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.page.PageInfoRequest;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesPageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.service.BakeryFavoriteService;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.BakeryFavoritePageService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryFavoriteController {
	private final BakeryFavoriteService bakeryFavoriteService;
	private final BakeryFavoritePageService bakeryFavoritePageService;

	@PostMapping("/{bakery_id}/like")
	public ApiSuccessResponse<Map<String, Long>> likeBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long bakeryFavoriteId = bakeryFavoriteService.likeBakery(customerId, bakeryId);
		return ApiSuccessResponse.of("bakeryFavoriteId", bakeryFavoriteId);
	}

	@DeleteMapping("/{bakery_id}/like")
	public ApiSuccessResponse<Map<String, Long>> deleteBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long bakeryFavoriteId = bakeryFavoriteService.deleteBakery(customerId, bakeryId);
		return ApiSuccessResponse.of("bakeryFavoriteId", bakeryFavoriteId);
	}

	@GetMapping("/like")
	public ApiSuccessResponse<BakeryFavoritesPageResponse> getFavorites(
		@AuthCustomer Long customerId, PageInfoRequest pageInfoRequest) {
		return ApiSuccessResponse.of(bakeryFavoritePageService.getFavoriteBakery(customerId, pageInfoRequest));
	}
}
