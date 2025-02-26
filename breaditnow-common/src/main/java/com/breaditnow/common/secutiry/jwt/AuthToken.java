package com.breaditnow.common.secutiry.jwt;

import lombok.Builder;

@Builder
public record AuthToken(
	Long userId,
	String token,
	Long expiresIn,
	String expiryDate,
	AuthTokenType type
) {

}
