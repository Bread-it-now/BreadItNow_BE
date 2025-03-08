package com.breaditnow.customer.domain.bakeryfavorite.controller.res;

import java.util.List;

import com.breaditnow.common.page.PageInfo;

import lombok.Builder;

@Builder
public record BakeryFavoritesPageResponse(
	List<BakeryFavoritesResponse> favorites,
	PageInfo pageInfo
) {
	public static BakeryFavoritesPageResponse of(List<BakeryFavoritesResponse> favorites, PageInfo pageInfo) {
		return BakeryFavoritesPageResponse.builder()
			.favorites(favorites)
			.pageInfo(pageInfo)
			.build();
	}
}
