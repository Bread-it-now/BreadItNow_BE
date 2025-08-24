package com.breaditnow.auth.application.dto.request;

public record DirectLoginRequest(
        String email,
        String password
) {
}
