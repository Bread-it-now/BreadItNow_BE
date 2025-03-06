package com.breaditnow.owner.domain.product.controller.req;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;

public record ProductCreateRequest(
	String productType,
	Long[] breadCategoryIds,
	String name,
	Integer price,
	String description,
	String[] releaseTimes
) {
	public Product toEntity(Bakery bakery, String productImageUrl) {
		return Product.builder()
			.bakery(bakery)
			.type(ProductType.from(productType))
			.name(name)
			.price(price)
			.description(description)
			.releaseTime(String.join(";", releaseTimes))
			.image(productImageUrl)
			.stock(0)
			.isActive(false)
			.build();
	}
}
