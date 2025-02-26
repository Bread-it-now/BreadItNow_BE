package com.breaditnow.common.secutiry.jwt.token;

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
