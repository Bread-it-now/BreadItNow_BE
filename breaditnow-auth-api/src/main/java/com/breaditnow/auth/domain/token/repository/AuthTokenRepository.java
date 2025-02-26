package com.breaditnow.auth.domain.token.repository;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.breaditnow.auth.global.exception.AuthException;
import com.breaditnow.common.security.jwt.token.AuthToken;
import com.breaditnow.common.security.jwt.token.AuthTokenType;
import com.breaditnow.redis.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthTokenRepository {
	private final RedisRepository redisRepository;
	private final ObjectMapper objectMapper;

	private String generateTokenKey(AuthTokenType type, Long userId) {
		return String.format("TOKEN:%s:%d", type.name(), userId);
	}

	public void saveToken(AuthToken authToken) {
		String key = generateTokenKey(authToken.type(), authToken.userId());
		try {
			String tokenJson = objectMapper.writeValueAsString(authToken);
			redisRepository.save(key, tokenJson, authToken.expiresIn());
		} catch (JsonProcessingException e) {
			throw new AuthException(SERIALIZATION_ERROR);
		}
	}

	public Optional<AuthToken> findToken(AuthTokenType type, Long userId) {
		String key = generateTokenKey(type, userId);
		Optional<Object> result = redisRepository.find(key);
		if (result.isPresent() && result.get() instanceof String) {
			try {
				AuthToken token = objectMapper.readValue((String)result.get(), AuthToken.class);
				return Optional.of(token);
			} catch (JsonProcessingException e) {
				throw new AuthException(DESERIALIZATION_ERROR);
			}
		}
		return Optional.empty();
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
}
