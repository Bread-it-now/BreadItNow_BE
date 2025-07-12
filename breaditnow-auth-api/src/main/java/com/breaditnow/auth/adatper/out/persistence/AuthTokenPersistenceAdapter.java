package com.breaditnow.auth.adatper.out.persistence;

import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class AuthTokenPersistenceAdapter implements AuthTokenRepository {
    private final StringRedisTemplate redisTemplate;
    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    @Override
    public void saveRefreshToken(AuthToken refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken.userId();
        String value = refreshToken.token();
        Duration timeout = Duration.ofMillis(refreshToken.expiresIn());
        redisTemplate.opsForValue().set(key, value, timeout);
    }
}
