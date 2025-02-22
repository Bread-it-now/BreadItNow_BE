package com.breaditnow.auth.domain.token.domain;

import lombok.Builder;

@Builder
public record AuthToken(
	Long userId,
	String token,
	Long expiresIn,
	AuthTokenType type
) {

}
