package com.breaditnow.owner.domain.bakery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.OperatingStatusRequest;
import com.breaditnow.owner.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.domain.bakery.service.BakeryService;

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
		@RequestPart("data") BakeryCreateRequest request,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

		Long bakeryId = bakeryService.createBakery(ownerId, request, profileImage);
		return ApiSuccessResponse.of("bakeryId", bakeryId);
	}

	@GetMapping("/{bakeryId}")
	public ApiSuccessResponse<BakeryResponse> getBakery(@PathVariable("bakeryId") Long bakeryId) {
		return ApiSuccessResponse.of(bakeryService.getBakery(bakeryId));
	}

	@PutMapping("/{bakeryId}/{ownerId}")
	public ApiSuccessResponse<BakeryResponse> updateBakery(@PathVariable("bakeryId") Long bakeryId,
		@PathVariable("ownerId") Long ownerId,
		@RequestPart("data") BakeryUpdateRequest request,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
		@RequestPart(value = "bakeryImages", required = false) List<MultipartFile> bakeryImages
	) {
		return ApiSuccessResponse.of(
			bakeryService.updateBakery(ownerId, bakeryId, request, profileImage, bakeryImages));
	}

	@PatchMapping("/{bakeryId}/{ownerId}/operating-status")
	public ApiSuccessResponse<Map<String, Long>> updateOperatingBakery(@PathVariable("bakeryId") Long bakeryId,
		@PathVariable("ownerId") Long ownerId, @RequestBody OperatingStatusRequest request) {
		Long savedBakeryId = bakeryService.updateOperatingStatus(ownerId, bakeryId, request.operatingStatus());
		return ApiSuccessResponse.of("bakeryId", savedBakeryId);
	}

	@DeleteMapping("/{bakeryId}/{ownerId}")
	public ApiSuccessResponse<Map<String, Long>> deleteBakery(@PathVariable("ownerId") Long ownerId,
		@PathVariable("bakeryId") Long bakeryId) {
		Long deletedBakeryId = bakeryService.deleteBakery(ownerId, bakeryId);
		return ApiSuccessResponse.of("bakeryId", deletedBakeryId);
	}
}
