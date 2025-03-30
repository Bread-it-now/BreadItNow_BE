package com.breaditnow.customer.domain.bakery.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.domain.bakery.service.BakeryFavoritePageService;
import com.breaditnow.customer.domain.bakery.service.BakeryFavoriteService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryFavoriteController {
	private final BakeryFavoriteService bakeryFavoriteService;
	private final BakeryFavoritePageService bakeryFavoritePageService;

	@PostMapping("/{bakery_id}/favorite")
	public ApiSuccessResponse<Map<String, Long>> likeBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long savedBakeryId = bakeryFavoriteService.likeBakery(customerId, bakeryId);
		return ApiSuccessResponse.of("bakeryId", savedBakeryId);
	}

	@DeleteMapping("/{bakery_id}/favorite")
	public ApiSuccessResponse<Map<String, Long>> deleteBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long savedBakeryId = bakeryFavoriteService.deleteBakery(customerId, bakeryId);
		return ApiSuccessResponse.of("bakeryId", savedBakeryId);
	}

	@GetMapping("/favorite")
	public ApiSuccessResponse<BakeryFavoritePageResponse> getFavorites(@AuthCustomer Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "latest") String sort,
		@Valid GeoPointRequest geoPointRequest) {
		return ApiSuccessResponse.of(
			bakeryFavoritePageService.getFavorites(customerId, page, size, sort, geoPointRequest));
	}
}
