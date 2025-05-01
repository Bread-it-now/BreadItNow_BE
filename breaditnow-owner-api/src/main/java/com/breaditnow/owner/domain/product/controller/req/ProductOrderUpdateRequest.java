package com.breaditnow.owner.domain.product.controller.req;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ProductOrderUpdateRequest(
	@NotNull(message = "상품 순서 리스트는 필수입니다.")
	@Schema(description = "상품 순서 아이템 목록", example = "[{ \"productId\": 1001, \"order\": 1 }, { \"productId\": 1002, \"order\": 2 }]")
	List<ProductOrderItemRequest> productOrders
) {
}
