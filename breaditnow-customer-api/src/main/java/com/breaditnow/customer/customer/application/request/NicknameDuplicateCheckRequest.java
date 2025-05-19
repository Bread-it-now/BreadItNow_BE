package com.breaditnow.customer.customer.application.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record NicknameDuplicateCheckRequest(
        @Schema(description = "닉네임", example = "updateNickname", required = true)
        String nickname
) {
}
