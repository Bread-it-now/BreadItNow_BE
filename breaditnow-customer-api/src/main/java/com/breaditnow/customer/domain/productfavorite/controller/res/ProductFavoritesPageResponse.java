package com.breaditnow.customer.domain.productfavorite.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record ProductFavoritesPageResponse(
	List<ProductFavoriteResponse> favorites,
	PageInfo pageInfo
) {
	public static ProductFavoritesPageResponse of(Page<Product> favoritesPage) {
		List<ProductFavoriteResponse> favorites = favoritesPage.stream()
			.map(ProductFavoriteResponse::of)
			.toList();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(favoritesPage.getTotalElements())
			.totalPages(favoritesPage.getTotalPages())
			.isLast(favoritesPage.isLast())
			.currPage(favoritesPage.getPageable().getPageNumber())
			.build();

		return ProductFavoritesPageResponse.builder()
			.favorites(favorites)
			.pageInfo(pageInfo)
			.build();
	}
}
