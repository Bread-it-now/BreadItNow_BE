package com.breaditnow.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneSendCodeRequest(
        @NotBlank @Pattern(regexp="^01[016789]-\\d{3,4}-\\d{4}$")
        String phone
) {}
