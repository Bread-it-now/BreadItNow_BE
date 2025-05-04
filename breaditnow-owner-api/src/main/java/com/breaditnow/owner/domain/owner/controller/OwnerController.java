package com.breaditnow.owner.domain.owner.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.owner.controller.req.OwnerPasswordUpdateRequest;
import com.breaditnow.owner.domain.owner.service.OwnerService;
import com.breaditnow.owner.global.security.annotation.AuthOwner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner")
public class OwnerController implements OwnerControllerDocs {

	private final OwnerService ownerService;

	@PatchMapping("/me/password")
	public ApiSuccessResponse<Void> updateOwnerPassword(
		@AuthOwner Long ownerId,
		@RequestBody @Valid OwnerPasswordUpdateRequest request
	) {
		ownerService.updateOwnerPassword(ownerId, request);
		return ApiSuccessResponse.of();
	}
}
