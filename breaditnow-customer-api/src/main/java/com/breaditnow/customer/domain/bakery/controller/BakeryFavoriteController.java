package com.breaditnow.customer.domain.bakery.controller;

import static com.breaditnow.common.response.ApiSuccessResponse.*;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.enumerate.FavoriteSortType;
import com.breaditnow.customer.domain.bakery.controller.res.FavoritesResponse;
import com.breaditnow.customer.domain.bakery.service.BakeryFavoriteService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryFavoriteController {
	private final BakeryFavoriteService bakeryFavoriteService;

	@PostMapping("/{bakery_id}/like")
	public ApiSuccessResponse<Map<String, Long>> likeBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long bakeryFavoriteId = bakeryFavoriteService.likeBakery(customerId, bakeryId);
		return of("bakeryFavoriteId", bakeryFavoriteId);
	}

	@DeleteMapping("/{bakery_id}/like")
	public ApiSuccessResponse<Map<String, Long>> deleteBakery(@AuthCustomer Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		Long bakeryFavoriteId = bakeryFavoriteService.deleteBakery(customerId, bakeryId);
		return of("bakeryFavoriteId", bakeryFavoriteId);
	}

	@GetMapping("/like")
	public ApiSuccessResponse<List<FavoritesResponse>> getFavorites(
		@AuthCustomer Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "LATEST") FavoriteSortType sortType) {

		Sort sort = sortType.getSort();
		Pageable pageable = PageRequest.of(page, size, sort);

		List<FavoritesResponse> response = bakeryFavoriteService.getFavorites(customerId, pageable);
		return of(response);
	}
}
