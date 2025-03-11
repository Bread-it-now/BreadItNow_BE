package com.breaditnow.owner.domain.product.controller.req;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
	@NotNull(message = "메뉴 타입은 필수 항목입니다.") String productType,
	Long[] breadCategoryIds,
	@NotBlank(message = "빵집 이름은 필수 항목입니다.") String name,
	@NotNull(message = "메뉴 가격은 필수 항목입니다.") Integer price,
	String description,
	String[] releaseTimes
) {
	public Product toEntity(Bakery bakery, String productImageUrl) {
		String joinedReleaseTimes =
			(releaseTimes != null && releaseTimes.length > 0) ? String.join(";", releaseTimes) : null;
		return Product.builder()
			.bakery(bakery)
			.type(ProductType.from(productType))
			.name(name)
			.price(price)
			.description(description != null ? description : "")
			.releaseTime(joinedReleaseTimes)
			.image(productImageUrl)
			.stock(0)
			.isActive(true)
			.build();
	}
}
