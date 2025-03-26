package com.breaditnow.owner.domain.fcm.controller.req;

import jakarta.validation.constraints.NotBlank;

public record FcmSendRequest(
	@NotBlank String fcmToken
) {
}
