//package com.breaditnow.owner.domain.product.controller.res;
//
//import com.breaditnow.domain.domain.product.entity.Product;
//import com.breaditnow.owner.domain.breadcategory.controller.res.BreadCategoryResponse;
//import com.breaditnow.owner.product.domain.ProductType;
//import com.fasterxml.jackson.annotation.JsonInclude;
//
//import java.util.Arrays;
//import java.util.List;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public record ProductResponse(
//	Long productId,
//	Long bakeryId,
//	String productType,
//	String name,
//	int price,
//	String image,
//	String description,
//	List<String> releaseTimes,
//	int stock,
//	boolean isActive,
//	boolean isHidden,
//	List<BreadCategoryResponse> breadCategories,
//	int displayOrder
//) {
//	public static ProductResponse of(Product product) {
//		List<BreadCategoryResponse> breadCategoryResponses = null;
//		if (product.getType() == ProductType.BREAD) {
//			breadCategoryResponses = product.getBreadCategories().stream()
//				.map(relation -> BreadCategoryResponse.of(relation.getBreadCategory()))
//				.toList();
//		}
//
//		List<String> releaseTimes = null;
//		if (product.getReleaseTime() != null) {
//			releaseTimes = Arrays.stream(product.getReleaseTime().split(";"))
//				.map(String::trim)
//				.filter(s -> !s.isEmpty())
//				.toList();
//		}
//
//		return new ProductResponse(
//			product.getId(),
//			product.getBakery().getId(),
//			product.getType().name(),
//			product.getName(),
//			product.getPrice(),
//			product.getImage(),
//			product.getDescription(),
//			releaseTimes,
//			product.getStock(),
//			product.isActive(),
//			product.isHidden(),
//			breadCategoryResponses,
//			product.getDisplayOrder()
//		);
//	}
//}
