package com.breaditnow.customer.domain.bakery.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.customer.domain.bakery.service.BakeryService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryController {
	private final BakeryService bakeryService;

	@GetMapping("/{bakery_id}/detail")
	public ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(@AuthCustomer(required = false) Long customerId,
		@PathVariable("bakery_id") Long bakeryId) {
		return ApiSuccessResponse.of(bakeryService.getBakeryDetail(customerId, bakeryId));
	}

	/**
	 * 핫한 빵집 조회하기
	 */
	@GetMapping("/hot")
	public ApiSuccessResponse<HotBakeryPageResponse> searchHotBakeries(@AuthCustomer Long customerId,
		@PageableDefault Pageable pageable) {
		return ApiSuccessResponse.of(bakeryService.searchHotBakeries(customerId, pageable));
	}
}
