package com.breaditnow.customer.domain.bakery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.service.BakeryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryController {
	private final BakeryService bakeryService;

	@GetMapping("/{bakery_id}/detail")
	public ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(@PathVariable("bakery_id") Long bakeryId) {
		return ApiSuccessResponse.of(bakeryService.getBakeryDetail(bakeryId));
	}
}
