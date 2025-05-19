package com.breaditnow.customer.fcm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.fcm.controller.req.FcmSendRequest;
import com.breaditnow.customer.fcm.service.FcmService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController implements FcmControllerDocs {
	private final FcmService fcmService;

	@PostMapping("/update-token")
	public ApiSuccessResponse<Void> pushMessage(@AuthCustomer Long customerId,
		@RequestBody @Valid FcmSendRequest fcmSendRequest) {

		fcmService.updateToken(customerId, fcmSendRequest);

		return ApiSuccessResponse.of();
	}
}
