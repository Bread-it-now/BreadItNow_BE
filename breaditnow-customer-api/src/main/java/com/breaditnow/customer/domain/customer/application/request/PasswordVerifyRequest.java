package com.breaditnow.customer.domain.customer.application.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PasswordVerifyRequest(
        @Schema(description = "비밀번호", example = "password12345!", required = true)
        String password
) {
}
