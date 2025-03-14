package com.breaditnow.customer.domain.bakeryfavorite.controller;

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
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.service.BakeryFavoritePageService;
import com.breaditnow.customer.domain.bakeryfavorite.service.BakeryFavoriteService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import jakarta.validation.Valid;
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
		Long savedBakeryId = bakeryFavoriteService.likeBakery(customerId, bakeryId);
		return ApiSuccessResponse.of("bakeryId", savedBakeryId);
	}

	@DeleteMapping("/{bakery_id}/like")
	public ApiSuccessResponse<Map<String, Long>> deleteBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long savedBakeryId = bakeryFavoriteService.deleteBakery(customerId, bakeryId);
		return ApiSuccessResponse.of("bakeryId", savedBakeryId);
	}

	@GetMapping("/like")
	public ApiSuccessResponse<BakeryFavoritePageResponse> getFavorites(@AuthCustomer Long customerId,
		Pageable pageable, @Valid GeoPointRequest geoPointRequest) {
		return ApiSuccessResponse.of(
			bakeryFavoritePageService.getFavorites(customerId, pageable, geoPointRequest));
	}

}
