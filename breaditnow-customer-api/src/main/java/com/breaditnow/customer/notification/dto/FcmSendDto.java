package com.breaditnow.customer.notification.dto;

import lombok.Builder;

@Builder
public record FcmSendDto(
	String token,
	String title,
	String body
) {
}
