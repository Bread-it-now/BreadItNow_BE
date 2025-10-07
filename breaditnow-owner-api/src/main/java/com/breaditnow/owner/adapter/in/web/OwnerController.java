package com.breaditnow.owner.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthOwner;
import com.breaditnow.owner.adapter.in.web.dto.request.FcmTokenUpdateRequest;
import com.breaditnow.owner.adapter.in.web.dto.request.NicknameCreateRequest;
import com.breaditnow.owner.domain.port.in.OwnerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner")
public class OwnerController {
	private final OwnerUseCase ownerUseCase;

	@GetMapping("/me/init")
	public ApiSuccessResponse<Map<String, Boolean>> getOwnerInit(@AuthOwner Long ownerId) {
		boolean isInitialized = ownerUseCase.isOwnerInitialized(ownerId);
		return ApiSuccessResponse.of("isInitialized", isInitialized);
	}

	@PostMapping("/me/init")
	public ApiSuccessResponse<Void> createOwnerInit(@AuthOwner Long ownerId, @RequestBody @Valid NicknameCreateRequest request) {
		ownerUseCase.createOwnerInit(ownerId, request.nickname());
		return ApiSuccessResponse.of(null);
	}


	@PatchMapping("/me/fcm-token")
	public ApiSuccessResponse<Void> updateFcmToken(@AuthOwner Long ownerId, @RequestBody @Valid FcmTokenUpdateRequest request) {
		ownerUseCase.updateFcmToken(ownerId, request.fcmToken());
		return ApiSuccessResponse.of(null);
	}
}
