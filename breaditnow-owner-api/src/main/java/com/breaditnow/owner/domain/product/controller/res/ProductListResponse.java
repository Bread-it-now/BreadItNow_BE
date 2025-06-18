//package com.breaditnow.owner.domain.product.controller.res;
//
//import com.breaditnow.domain.domain.product.entity.Product;
//import com.breaditnow.owner.product.domain.ProductType;
//
//import java.util.List;
//
//public record ProductListResponse(
//	int totalCount,
//	List<ProductResponse> breadProducts,
//	List<ProductResponse> otherProducts
//) {
//
//	public static ProductListResponse of(List<Product> products) {
//		List<ProductResponse> breadProducts = products.stream()
//			.filter(product -> product.getType() == ProductType.BREAD)
//			.map(ProductResponse::of)
//			.toList();
//		List<ProductResponse> otherProducts = products.stream()
//			.filter(product -> product.getType() == ProductType.OTHER)
//			.map(ProductResponse::of)
//			.toList();
//		return new ProductListResponse(products.size(), breadProducts, otherProducts);
//	}
//}
