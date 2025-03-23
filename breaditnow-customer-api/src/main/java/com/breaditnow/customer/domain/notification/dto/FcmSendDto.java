package com.breaditnow.customer.domain.notification.dto;

import lombok.Builder;

@Builder
public record FcmSendDto(
	String token,
	String title,
	String body
) {
}
