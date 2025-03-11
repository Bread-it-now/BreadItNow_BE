package com.breaditnow.owner.domain.product.controller.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductOrderItemRequest(
	@NotNull(message = "메뉴 ID는 필수입니다.")
	Long productId,
	@Min(value = 0, message = "순서는 0 이상의 값이어야 합니다.")
	int order
) {
}
