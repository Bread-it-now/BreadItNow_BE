package com.breaditnow.owner.domain.product.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductHiddenRequest(
        @NotNull(message = "hidden 타입은 필수입니다.")
        Boolean hidden,

        @NotEmpty(message = "상품 숨김은 ID 목록이 필수입니다.")
        @Schema(description = "숨길 처리할 상품 ID 목록", example = "[1, 2, 3]")
        List<Long> productIds
) {
}
