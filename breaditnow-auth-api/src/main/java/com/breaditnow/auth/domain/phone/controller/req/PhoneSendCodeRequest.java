package com.breaditnow.auth.domain.phone.controller.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneSendCodeRequest(
        @NotBlank @Pattern(regexp="^01[016789]-\\d{3,4}-\\d{4}$")
        String phone
) {}
