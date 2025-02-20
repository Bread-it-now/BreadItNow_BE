package com.breaditnow.auth.global.security.oauth2.model;

import static com.breaditnow.auth.global.exception.AuthenticationErrorCode.*;
import static com.breaditnow.domain.customer.enumerate.Provider.*;

import java.util.Map;
import java.util.Objects;

import com.breaditnow.auth.global.exception.AuthException;
import com.breaditnow.domain.customer.enumerate.Provider;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record OAuth2UserInfo (
	String oauth2Id,
	Provider provider
){

	public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
		Provider provider = Provider.valueOf(registrationId);
		return switch (provider) {
			case KAKAO -> ofKakao(attributes);
			case NAVER -> ofNaver(attributes);
			case GOOGLE -> ofGoogle(attributes);
			default -> throw new AuthException(UNSUPPORTED_PROVIDER);
		};
	}

	private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
		String oauth2Id = Objects.toString(attributes.get("id"));
		return OAuth2UserInfo.builder()
			.oauth2Id(oauth2Id)
			.provider(KAKAO)
			.build();
	}

	private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
		String oauth2Id = Objects.toString(response.get("id"));
		return OAuth2UserInfo.builder()
			.oauth2Id(oauth2Id)
			.provider(NAVER)
			.build();
	}

	private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
		String oauth2Id = Objects.toString(attributes.get("sub"));
		return OAuth2UserInfo.builder()
			.oauth2Id(oauth2Id)
			.provider(GOOGLE)
			.build();
	}

}
