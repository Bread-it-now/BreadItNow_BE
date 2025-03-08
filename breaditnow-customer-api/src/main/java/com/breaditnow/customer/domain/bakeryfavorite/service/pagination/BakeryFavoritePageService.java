package com.breaditnow.customer.domain.bakeryfavorite.service.pagination;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesPageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesResponse;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy.BakeryFavoriteSortFactory;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy.BakeryFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.BakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final BakeryFavoriteRepository bakeryFavoriteRepository;
	private final BakeryFavoriteSortFactory bakeryFavoriteSortFactory;

	public BakeryFavoritesPageResponse getFavoriteBakery(Long customerId, int page, int size, String sort) {
		BakeryFavoriteSortStrategy strategy = bakeryFavoriteSortFactory.getStrategy(sort);
		Pageable pageable = PageRequest.of(page, size, strategy.getSort());

		Page<BakeryFavorite> favoritesPage = strategy.getFavoritePage(bakeryFavoriteRepository, customerId, pageable);

		List<BakeryFavoritesResponse> favoriteItems = favoritesPage.getContent().stream()
			.map(favorite -> new BakeryFavoritesResponse(
				favorite.getBakery().getId(),
				favorite.getBakery().getName(),
				favorite.getBakery().getProfileImage(),
				0
			))
			.toList();

		PageInfo pageInfo = PageInfo.of(favoritesPage.getTotalElements(), favoritesPage.getTotalPages(),
			favoritesPage.isLast(), favoritesPage.getPageable().getPageNumber());
		return BakeryFavoritesPageResponse.of(favoriteItems, pageInfo);
	}
}
