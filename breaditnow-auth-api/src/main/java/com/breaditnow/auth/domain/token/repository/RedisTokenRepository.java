package com.breaditnow.auth.domain.token.repository;

import static com.breaditnow.auth.domain.token.domain.AuthTokenType.*;
import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.breaditnow.auth.domain.token.domain.AuthToken;
import com.breaditnow.auth.domain.token.domain.AuthTokenType;
import com.breaditnow.auth.global.exception.AuthException;
import com.breaditnow.redis.repository.RedisRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {
	private final RedisRepository redisRepository;

	private String generateTokenKey(AuthTokenType type, Long userId) {
		return String.format("TOKEN:%s:%d", type.name(), userId);
	}

	public void saveToken(AuthToken authToken) {
		String key = generateTokenKey(authToken.type(), authToken.customerId());
		redisRepository.save(key, authToken.token(), authToken.expiresIn());
	}

	public Optional<String> findToken(AuthTokenType type, Long userId) {
		String key = generateTokenKey(type, userId);
		return redisRepository.find(key);
	}

	public void deleteToken(AuthTokenType type, Long userId) {
		String key = generateTokenKey(type, userId);
		redisRepository.delete(key);
	}

	public void deleteAllTokens(Long userId) {
		for (AuthTokenType type : AuthTokenType.values()) {
			String key = generateTokenKey(type, userId);
			redisRepository.delete(key);
		}
	}

	public String getOAuth2AccessToken(Long customerId) {
		return findToken(OAUTH2_ACCESS, customerId)
			.orElseThrow(() -> new AuthException(OAUTH2_ACCESS_TOKEN_EXPIRED,
				"customer=" + customerId));
	}
}
