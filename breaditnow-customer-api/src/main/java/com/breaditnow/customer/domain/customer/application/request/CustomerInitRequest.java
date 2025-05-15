package com.breaditnow.customer.domain.customer.application.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CustomerInitRequest(
        @Schema(description = "사용자 닉네임", example = "소금빵러버")
        String nickname,

        @Schema(description = "선호하는 빵 카테고리 ID 목록", example = "[1, 2, 3]")
        List<Long> breadCategoryIds
) {
}
