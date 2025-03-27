package com.breaditnow.owner.domain.product.controller.req;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record ProductHiddenRequest(
	@NotEmpty(message = "메뉴 숨김은 ID 목록이 필수입니다.")
	List<Long> productIds
) {
}
