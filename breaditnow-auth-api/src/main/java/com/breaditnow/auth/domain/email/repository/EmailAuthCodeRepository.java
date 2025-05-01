package com.breaditnow.auth.domain.email.repository;

import com.breaditnow.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailAuthCodeRepository {

    private static final String KEY_PREFIX = "email:auth-code:";

    private final RedisRepository redis;

    public void save(String email, String code, Duration ttl) {
        redis.save(KEY_PREFIX + email, code, ttl.toMillis());
    }

    public Optional<String> find(String email) {
        return redis.find(KEY_PREFIX + email).map(Object::toString);
    }

    public void delete(String email) {
        redis.delete(KEY_PREFIX + email);
    }
}
