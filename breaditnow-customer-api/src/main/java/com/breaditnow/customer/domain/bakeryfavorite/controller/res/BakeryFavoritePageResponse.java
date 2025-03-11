package com.breaditnow.customer.domain.bakeryfavorite.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.bakery.entity.Bakery;

import lombok.Builder;

@Builder
public record BakeryFavoritePageResponse(
	List<BakeryFavoriteResponse> favorites,
	PageInfo pageInfo
) {
	public static BakeryFavoritePageResponse of(List<BakeryFavoriteResponse> favorites, Page<Bakery> favoritesPage) {
		PageInfo pageInfo = PageInfo.builder()
			.totalElements(favoritesPage.getTotalElements())
			.totalPages(favoritesPage.getTotalPages())
			.isLast(favoritesPage.isLast())
			.currPage(favoritesPage.getPageable().getPageNumber())
			.build();

		return BakeryFavoritePageResponse.builder()
			.favorites(favorites)
			.pageInfo(pageInfo)
			.build();
	}
}
