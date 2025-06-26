package com.breaditnow.owner.adapter.in.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthOwner;
import com.breaditnow.owner.domain.port.in.OwnerUseCase;
import com.breaditnow.owner.adapter.in.presentation.req.ChangePasswordRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner")
public class OwnerController {
	private final OwnerUseCase ownerUseCase;

	@PatchMapping("/me/password")
	public ApiSuccessResponse<Void> updateOwnerPassword(
		@AuthOwner Long ownerId,
		@RequestBody @Valid ChangePasswordRequest request
	) {
		ownerUseCase.changePassword(ownerId, request.newPassword());
		return ApiSuccessResponse.of(null);
	}
}
