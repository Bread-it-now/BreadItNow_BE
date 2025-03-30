package com.breaditnow.customer.domain.product.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record HotProductPageResponse(
	List<HotProductResponse> hotProducts,
	PageInfo pageInfo
) {
	public static HotProductPageResponse of(Page<Product> productPage) {
		List<HotProductResponse> hotProductResponses = productPage.stream()
			.map(HotProductResponse::of)
			.toList();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(productPage.getTotalElements())
			.totalPages(productPage.getTotalPages())
			.isLast(productPage.isLast())
			.currPage(productPage.getPageable().getPageNumber())
			.build();

		return HotProductPageResponse.builder()
			.hotProducts(hotProductResponses)
			.pageInfo(pageInfo)
			.build();
	}
}
