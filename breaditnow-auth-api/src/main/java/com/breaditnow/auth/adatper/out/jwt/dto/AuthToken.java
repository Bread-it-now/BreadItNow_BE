package com.breaditnow.auth.adatper.out.jwt.dto;

import lombok.Builder;

@Builder
public record AuthToken(
        Long userId,
        String token,
        Long expiresIn,
        String expiryDate,
        AuthTokenType type
) {
}
