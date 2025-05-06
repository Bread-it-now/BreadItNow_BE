package com.breaditnow.auth.domain.phone.controller.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PhoneVerifyCodeRequest(
        @NotBlank @Pattern(regexp="^01[016789]-\\d{3,4}-\\d{4}$")
        String phone,
        @NotBlank @Size(min=6,max=6) String code
) {}
