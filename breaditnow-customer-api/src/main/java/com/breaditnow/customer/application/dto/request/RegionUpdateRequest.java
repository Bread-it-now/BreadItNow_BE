package com.breaditnow.customer.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RegionUpdateRequest(
        @Schema(description = "시도 코드", example = "11")
        String sidoCode,

        @Schema(description = "구군 코드 목록", example = "[\"110\", \"140\"]")
        List<String> gugunCodes
) {
}
