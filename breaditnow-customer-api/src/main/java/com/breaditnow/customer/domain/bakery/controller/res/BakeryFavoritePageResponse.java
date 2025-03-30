package com.breaditnow.customer.domain.bakery.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.favorite.dto.BakeryFavoriteDto;

import lombok.Builder;

@Builder
public record BakeryFavoritePageResponse(
	List<BakeryFavoriteResponse> favorites,
	PageInfo pageInfo
) {
	public static BakeryFavoritePageResponse of(Page<BakeryFavoriteDto> favoritesPage) {
		PageInfo pageInfo = PageInfo.builder()
			.totalElements(favoritesPage.getTotalElements())
			.totalPages(favoritesPage.getTotalPages())
			.isLast(favoritesPage.isLast())
			.currPage(favoritesPage.getPageable().getPageNumber())
			.build();

		List<BakeryFavoriteResponse> favorites = favoritesPage.getContent().stream()
			.map(o -> BakeryFavoriteResponse.of(o.bakery(), o.distance()))
			.toList();

		return BakeryFavoritePageResponse.builder()
			.favorites(favorites)
			.pageInfo(pageInfo)
			.build();
	}
}
