package com.breaditnow.auth.domain.token.domain;

import static com.breaditnow.auth.domain.token.domain.AuthTokenType.*;

import org.springframework.security.oauth2.core.OAuth2AccessToken;

import lombok.Builder;

@Builder
public record AuthToken(
	Long customerId,
	String token,
	Long expiresIn,
	AuthTokenType type
) {

	public static AuthToken fromOAuth2AccessToken(Long customerId, OAuth2AccessToken oAuth2AccessToken) {
		long expiresIn = oAuth2AccessToken.getExpiresAt().toEpochMilli() - System.currentTimeMillis();
		return AuthToken.builder()
			.customerId(customerId)
			.token(oAuth2AccessToken.getTokenValue())
			.expiresIn(expiresIn)
			.type(OAUTH2_ACCESS)
			.build();
	}
}
