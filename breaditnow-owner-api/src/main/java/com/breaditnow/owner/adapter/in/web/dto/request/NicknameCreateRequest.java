package com.breaditnow.owner.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NicknameCreateRequest(
        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname
) {
}
