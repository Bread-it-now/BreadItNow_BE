package com.breaditnow.owner.domain.product.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ProductStockUpdateRequest(
	@NotNull(message = "재고 수량은 필수입니다.")
	@Schema(description = "업데이트할 상품의 재고 수량", example = "100")
	Integer stock
) {
}
