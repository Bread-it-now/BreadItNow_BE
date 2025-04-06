package com.breaditnow.owner.domain.fcm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.fcm.controller.req.FcmSendRequest;
import com.breaditnow.owner.domain.fcm.service.FcmService;
import com.breaditnow.owner.global.security.annotation.AuthOwner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController implements FcmControllerDocs {
	private final FcmService fcmService;

	@PostMapping("/update-token")
	public ApiSuccessResponse<Void> pushMessage(@AuthOwner Long ownerId,
		@RequestBody @Valid FcmSendRequest fcmSendRequest) {

		fcmService.updateToken(ownerId, fcmSendRequest);

		return ApiSuccessResponse.of();
	}
}
