package com.breaditnow.customer.domain.productfavorite.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record ProductFavoritePageResponse(
	List<ProductFavoriteResponse> favorites,
	PageInfo pageInfo
) {
	public static ProductFavoritePageResponse of(Page<Product> favoritesPage) {
		List<ProductFavoriteResponse> favorites = favoritesPage.stream()
			.map(ProductFavoriteResponse::of)
			.toList();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(favoritesPage.getTotalElements())
			.totalPages(favoritesPage.getTotalPages())
			.isLast(favoritesPage.isLast())
			.currPage(favoritesPage.getPageable().getPageNumber())
			.build();

		return ProductFavoritePageResponse.builder()
			.favorites(favorites)
			.pageInfo(pageInfo)
			.build();
	}
}
