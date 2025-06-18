//package com.breaditnow.owner.domain.product.controller.req;
//
//import com.breaditnow.domain.domain.bakery.entity.Bakery;
//import com.breaditnow.domain.domain.product.entity.Product;
//import com.breaditnow.domain.domain.product.enumerate.ProductType;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//public record ProductCreateRequest(
//	@NotNull(message = "상품 타입은 필수 항목입니다.")
//	@Schema(description = "상품 타입", example = "BREAD")
//	String productType,
//
//	@Schema(description = "상품에 해당하는 빵 카테고리 ID 배열", example = "[1, 2, 3]")
//	Long[] breadCategoryIds,
//
//	@Schema(description = "상품 이름", example = "식빵")
//	@NotBlank(message = "빵집 이름은 필수 항목입니다.")
//	String name,
//
//	@Schema(description = "상품 가격 (원 단위)", example = "1500")
//	@NotNull(message = "상품 가격은 필수 항목입니다.")
//	Integer price,
//
//	@Schema(description = "상품에 대한 상세 설명", example = "신선한 재료로 구워낸 바게트입니다.")
//	String description,
//
//	@Schema(description = "상품 출시 시간 배열", example = "[\"08:00\", \"12:00\"]")
//	String[] releaseTimes
//) {
//	public Product toEntity(Bakery bakery, String productImageUrl) {
//		String joinedReleaseTimes =
//			(releaseTimes != null && releaseTimes.length > 0) ? String.join(";", releaseTimes) : null;
//		return Product.builder()
//			.bakery(bakery)
//			.type(ProductType.from(productType))
//			.name(name)
//			.price(price)
//			.description(description != null ? description : "")
//			.releaseTime(joinedReleaseTimes)
//			.image(productImageUrl)
//			.stock(0)
//			.isActive(true)
//			.build();
//	}
//}
