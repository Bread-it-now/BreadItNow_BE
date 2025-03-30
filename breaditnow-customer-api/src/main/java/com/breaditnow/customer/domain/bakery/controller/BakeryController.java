package com.breaditnow.customer.domain.bakery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.customer.domain.bakery.service.BakeryPageService;
import com.breaditnow.customer.domain.bakery.service.BakeryService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryController {
	private final BakeryService bakeryService;
	private final BakeryPageService bakeryPageService;

	@GetMapping("/{bakery_id}/detail")
	public ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(@AuthCustomer(required = false) Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		return ApiSuccessResponse.of(bakeryService.getBakeryDetail(customerId, bakeryId));
	}

	/**
	 * 핫한 빵집 조회하기
	 */
	@GetMapping("/hot")
	public ApiSuccessResponse<HotBakeryPageResponse> searchHotBakeries(@AuthCustomer(required = false) Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "reservation") String sort,
		@Valid GeoPointRequest geoPointRequest) {
		return ApiSuccessResponse.of(
			bakeryPageService.searchHotBakeries(customerId, page, size, sort, geoPointRequest));
	}
}
