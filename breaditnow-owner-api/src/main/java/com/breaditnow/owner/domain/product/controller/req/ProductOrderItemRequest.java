package com.breaditnow.owner.domain.product.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductOrderItemRequest(
	@NotNull(message = "상품 ID는 필수입니다.")
	@Schema(description = "상품 ID", example = "1001")
	Long productId,
	@Min(value = 0, message = "순서는 0 이상의 값이어야 합니다.")
	@Schema(description = "상품 노출 순서", example = "1")
	int order
) {
}
