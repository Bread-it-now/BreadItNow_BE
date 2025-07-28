package com.breaditnow.auth.adatper.out.persistence;

import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

import static com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType.REFRESH;

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

    @Override
    public Optional<AuthToken> findRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        String token = redisTemplate.opsForValue().get(key);

        if (token == null) {
            return Optional.empty();
        }

        return Optional.of(new AuthToken(userId, token, null, null, REFRESH));
    }

    public void deleteRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }
}
