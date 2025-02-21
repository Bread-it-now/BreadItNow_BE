package com.breaditnow.redis.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
	private final RedisTemplate<String, Object> redisTemplate;
	private ValueOperations<String, Object> valueOperations;

	@PostConstruct
	private void init() {
		valueOperations = redisTemplate.opsForValue();
	}

	public void save(String key, Object value, Long expiresInMillis) {
		valueOperations.set(key, value);
		redisTemplate.expire(key, expiresInMillis, TimeUnit.MILLISECONDS);
	}

	public Optional<Object> find(String key) {
		return Optional.ofNullable(valueOperations.get(key));
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}

	public boolean exists(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}
}
