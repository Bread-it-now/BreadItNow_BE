/*
package com.breaditnow.customer.domain.bakeryfavorite.service.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesPageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy.BakeryFavoriteSortFactory;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy.BakeryFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final BakeryFavoriteSortFactory bakeryFavoriteSortFactory;

	public BakeryFavoritesPageResponse getFavorites(Long customerId, int page, int size, String sortType) {
		BakeryFavoriteSortStrategy strategy = bakeryFavoriteSortFactory.getStrategy(sortType);
		Pageable pageable = PageRequest.of(page, size);

		Page<CustomerBakeryFavorite> favoritesPage = strategy.getFavoritePage(customerId, pageable);
		return BakeryFavoritesPageResponse.of(favoritesPage);
	}
}
*/
