package com.breaditnow.email.controller.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendCodeRequest(
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "역할은 필수 항목입니다.")
        String role
) {}
