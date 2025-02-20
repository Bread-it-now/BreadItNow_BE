package com.breaditnow.owner.bakery.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.bakery.service.BakeryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryController {
	private final BakeryService bakeryService;

	@PostMapping
	public ApiSuccessResponse<Long> createBakery(Long ownerId, @RequestBody BakeryCreateRequest request) {
		return ApiSuccessResponse.of(bakeryService.createBakery(ownerId, request));
	}
}
