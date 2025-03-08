package com.breaditnow.customer.domain.bakery.controller.res;

import java.util.List;

import com.breaditnow.customer.domain.product.controller.res.ProductResponse;

import lombok.Builder;

@Builder
public record BakeryDetailResponse(
	BakeryResponse store,
	List<ProductResponse> products
) {
}
