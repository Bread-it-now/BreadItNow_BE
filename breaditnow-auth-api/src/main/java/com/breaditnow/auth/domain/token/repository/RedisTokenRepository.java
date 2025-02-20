package com.breaditnow.auth.domain.token.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.breaditnow.auth.domain.token.domain.AuthToken;
import com.breaditnow.auth.domain.token.domain.AuthTokenType;
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
		String key = generateTokenKey(authToken.type(), authToken.userId());
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
}
