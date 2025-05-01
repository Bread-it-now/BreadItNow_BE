package com.breaditnow.auth.global.security.jwt.token;

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
