package com.breaditnow.auth.application.dto.request;

public record DirectSignUpRequest(
        String email,
        String password,
        String role
) {
}
