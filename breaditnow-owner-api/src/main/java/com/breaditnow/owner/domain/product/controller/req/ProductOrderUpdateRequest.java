package com.breaditnow.owner.domain.product.controller.req;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record ProductOrderUpdateRequest(
	@NotNull(message = "메뉴 순서 리스트는 필수입니다.")
	List<ProductOrderItemRequest> orders
) {
}
