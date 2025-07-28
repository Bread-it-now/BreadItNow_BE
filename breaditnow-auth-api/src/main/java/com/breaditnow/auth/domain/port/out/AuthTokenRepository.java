package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;

import java.util.Optional;

public interface AuthTokenRepository {
    void saveRefreshToken(AuthToken refreshToken);
    Optional<AuthToken> findRefreshToken(Long userId);
    void deleteRefreshToken(Long userId);
}
