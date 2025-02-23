package com.breaditnow.owner.bakery.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.bakery.service.BakeryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
@Slf4j
public class BakeryController {
	private final BakeryService bakeryService;

	@PostMapping("/{ownerId}")
	public ApiSuccessResponse<Map<String, Long>> createBakery(@PathVariable("ownerId") Long ownerId,
		@RequestPart("data") BakeryCreateRequest bakeryCreateRequest,
		@RequestPart("file") MultipartFile profileImage) {
		try {
			log.info("data : {}", bakeryCreateRequest);
			log.info("profileImage = {}", profileImage);
			Long bakeryId = bakeryService.createBakery(ownerId, bakeryCreateRequest, profileImage);
			return ApiSuccessResponse.of("bakeryId", bakeryId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/{bakeryId}")
	public ApiSuccessResponse<BakeryResponse> getBakery(@PathVariable Long bakeryId) {
		return ApiSuccessResponse.of(bakeryService.getBakery(bakeryId));
	}

	@PutMapping("/{bakeryId}")
	public ApiSuccessResponse<BakeryResponse> updateBakery(Long ownerId, @PathVariable Long bakeryId,
		@RequestBody BakeryUpdateRequest request) {
		return ApiSuccessResponse.of(bakeryService.updateBakery(ownerId, bakeryId, request));
	}
}
