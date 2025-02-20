package com.breaditnow.owner.bakery.controller;

import java.util.Map;

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
	public ApiSuccessResponse<Map<String, Long>> createBakery(Long ownerId, @RequestBody BakeryCreateRequest request) {
		Long bakeryId = bakeryService.createBakery(ownerId, request);
		return ApiSuccessResponse.of("bakeryId", bakeryId);
	}
}
