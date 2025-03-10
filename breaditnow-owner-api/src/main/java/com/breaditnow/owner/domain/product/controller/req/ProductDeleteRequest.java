package com.breaditnow.owner.domain.product.controller.req;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record ProductDeleteRequest(
	@NotEmpty(message = "삭제할 메뉴 ID 목록은 필수입니다.")
	List<Long> productIds
) {
}
