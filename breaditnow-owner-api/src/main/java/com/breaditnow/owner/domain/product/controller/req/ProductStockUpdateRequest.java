package com.breaditnow.owner.domain.product.controller.req;

import jakarta.validation.constraints.NotNull;

public record ProductStockUpdateRequest(
	@NotNull(message = "재고 수량은 필수입니다.")
	Integer stock
) {
}
