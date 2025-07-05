//package com.breaditnow.token.repository;
//
//import java.util.Optional;
//
//import org.springframework.stereotype.Repository;
//
//import com.breaditnow.common.exception.AuthException;
//import com.breaditnow.auth_tmp.global.security.jwt.token.AuthToken;
//import com.breaditnow.auth_tmp.global.security.jwt.token.AuthTokenType;
//import com.breaditnow.redis.repository.RedisRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.RequiredArgsConstructor;
//
//@Repository
//@RequiredArgsConstructor
//public class AuthTokenRepository {
//	private final RedisRepository redisRepository;
//	private final ObjectMapper objectMapper;
//
//	private String generateTokenKey(AuthTokenType type, String role, Long userId) {
//		return String.format("TOKEN:%s:%s:%d", type.name(), role, userId);
//	}
//
//	public void saveToken(AuthToken authToken, String role) {
//		String key = generateTokenKey(authToken.type(), role, authToken.userId());
//		try {
//			String tokenJson = objectMapper.writeValueAsString(authToken);
//			redisRepository.save(key, tokenJson, authToken.expiresIn());
//		} catch (JsonProcessingException e) {
//			throw new AuthException(SERIALIZATION_ERROR);
//		}
//	}
//
//	public Optional<AuthToken> findToken(AuthTokenType type, String role, Long userId) {
//		String key = generateTokenKey(type, role, userId);
//		Optional<Object> result = redisRepository.find(key);
//		if (result.isPresent() && result.get() instanceof String) {
//			try {
//				AuthToken token = objectMapper.readValue((String)result.get(), AuthToken.class);
//				return Optional.of(token);
//			} catch (JsonProcessingException e) {
//				throw new AuthException(DESERIALIZATION_ERROR);
//			}
//		}
//		return Optional.empty();
//	}
//
//	public void deleteToken(AuthTokenType type, String role, Long userId) {
//		String key = generateTokenKey(type, role, userId);
//		redisRepository.delete(key);
//	}
//
//	public void deleteAllTokens(Long userId, String role) {
//		for (AuthTokenType type : AuthTokenType.values()) {
//			String key = generateTokenKey(type, role, userId);
//			redisRepository.delete(key);
//		}
//	}
//}
