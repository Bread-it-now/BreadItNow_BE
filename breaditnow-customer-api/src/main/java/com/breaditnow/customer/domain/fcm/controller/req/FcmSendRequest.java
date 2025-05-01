package com.breaditnow.customer.domain.fcm.controller.req;

import jakarta.validation.constraints.NotBlank;

public record FcmSendRequest(
	@NotBlank String fcmToken
) {
}
