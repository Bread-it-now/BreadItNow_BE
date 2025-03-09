package com.breaditnow.customer.domain.bakeryfavorite.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;

import lombok.Builder;

@Builder
public record BakeryFavoritesPageResponse(
	List<BakeryFavoritesResponse> favorites,
	PageInfo pageInfo
) {
	public static BakeryFavoritesPageResponse of(Page<CustomerBakeryFavorite> favoritesPage) {
		List<BakeryFavoritesResponse> favorites = favoritesPage.getContent().stream()
			.map(favorite -> new BakeryFavoritesResponse(
				favorite.getBakery().getId(),
				favorite.getBakery().getName(),
				favorite.getBakery().getProfileImage(),
				0
			))
			.toList();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(favoritesPage.getTotalElements())
			.totalPages(favoritesPage.getTotalPages())
			.isLast(favoritesPage.isLast())
			.currPage(favoritesPage.getPageable().getPageNumber())
			.build();

		return BakeryFavoritesPageResponse.builder()
			.favorites(favorites)
			.pageInfo(pageInfo)
			.build();
	}
}
