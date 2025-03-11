package com.breaditnow.customer.domain.bakery.controller.res;

import java.util.List;

import com.breaditnow.customer.domain.product.controller.res.ProductResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record BakeryDetailResponse(
	BakeryResponse store,
	List<ProductResponse> products
) {
	public static BakeryDetailResponse of(Bakery bakery, List<Product> products) {
		BakeryResponse bakeryResponse = BakeryResponse.of(bakery);
		List<ProductResponse> productResponses = products.stream()
			.map(product -> ProductResponse.of(product, bakery.getId()))
			.toList();

		return BakeryDetailResponse.builder()
			.store(bakeryResponse)
			.products(productResponses)
			.build();
	}
}
