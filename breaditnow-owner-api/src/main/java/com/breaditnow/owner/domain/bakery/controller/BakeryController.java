package com.breaditnow.owner.domain.bakery.controller;

import static org.springframework.http.MediaType.*;

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
import com.breaditnow.owner.global.security.annotation.AuthOwner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryController implements BakeryControllerDocs {
	private final BakeryService bakeryService;

	@PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
	public ApiSuccessResponse<Map<String, Long>> createBakery(@AuthOwner Long ownerId,
		@RequestPart("data") @Valid BakeryCreateRequest request,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
		Long bakeryId = bakeryService.createBakery(ownerId, request, profileImage);
		return ApiSuccessResponse.of("bakeryId", bakeryId);
	}

	@GetMapping("/{bakeryId}")
	public ApiSuccessResponse<BakeryResponse> getBakery(@PathVariable("bakeryId") Long bakeryId) {
		return ApiSuccessResponse.of(bakeryService.getBakery(bakeryId));
	}

	@PutMapping("/{bakeryId}")
	public ApiSuccessResponse<BakeryResponse> updateBakery(@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId,
		@RequestPart("data") BakeryUpdateRequest request,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
		@RequestPart(value = "bakeryImages", required = false) List<MultipartFile> bakeryImages
	) {
		return ApiSuccessResponse.of(
			bakeryService.updateBakery(ownerId, bakeryId, request, profileImage, bakeryImages));
	}

	@PatchMapping("/{bakeryId}/operating-status")
	public ApiSuccessResponse<Map<String, Long>> updateOperatingBakery(@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId,
		@RequestBody OperatingStatusRequest request) {
		Long savedBakeryId = bakeryService.updateOperatingStatus(ownerId, bakeryId, request.operatingStatus());
		return ApiSuccessResponse.of("bakeryId", savedBakeryId);
	}

	@DeleteMapping("/{bakeryId}")
	public ApiSuccessResponse<Map<String, Long>> deleteBakery(@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId) {
		Long deletedBakeryId = bakeryService.deleteBakery(ownerId, bakeryId);
		return ApiSuccessResponse.of("bakeryId", deletedBakeryId);
	}
}
