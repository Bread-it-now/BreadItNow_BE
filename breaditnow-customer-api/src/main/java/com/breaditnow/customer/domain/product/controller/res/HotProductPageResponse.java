package com.breaditnow.customer.domain.product.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.product.dto.ProductFavoriteDto;

import lombok.Builder;

@Builder
public record HotProductPageResponse(
	List<HotProductResponse> hotProducts,
	PageInfo pageInfo
) {
	public static HotProductPageResponse of(Page<ProductFavoriteDto> productFavoritePage) {
		List<HotProductResponse> hotProductResponses = productFavoritePage.stream()
			.map(productFavoriteDto -> HotProductResponse.of(productFavoriteDto.product(),
				productFavoriteDto.isFavorite()))
			.toList();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(productFavoritePage.getTotalElements())
			.totalPages(productFavoritePage.getTotalPages())
			.isLast(productFavoritePage.isLast())
			.currPage(productFavoritePage.getPageable().getPageNumber())
			.build();

		return HotProductPageResponse.builder()
			.hotProducts(hotProductResponses)
			.pageInfo(pageInfo)
			.build();
	}
}
