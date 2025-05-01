package com.breaditnow.owner.domain.product.controller.req;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record ProductDeleteRequest(
	@NotEmpty(message = "삭제할 상품 ID 목록은 필수입니다.")
	@Schema(description = "삭제할 상품 ID 목록", example = "[1, 2, 3]")
	List<Long> productIds
) {
}
