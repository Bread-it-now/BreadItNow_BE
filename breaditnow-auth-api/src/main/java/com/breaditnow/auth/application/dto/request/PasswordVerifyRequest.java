package com.breaditnow.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordVerifyRequest(
        @NotBlank(message = "비밀번호 값은 필수 입력 사항입니다.")
        String password
) {
}
