package com.breaditnow.email.controller.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyCodeRequest(
        @NotBlank @Email
        String email,
        @NotBlank
        String code
) {}
