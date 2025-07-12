package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;

public interface AuthTokenRepository {
    void saveRefreshToken(AuthToken refreshToken);
}
