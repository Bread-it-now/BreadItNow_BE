package com.breaditnow.customer.domain.bakeryfavorite.service.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.page.PageInfoRequest;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesPageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy.BakeryFavoriteSortFactory;
import com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy.BakeryFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final BakeryFavoriteSortFactory bakeryFavoriteSortFactory;

	public BakeryFavoritesPageResponse getFavoriteBakery(Long customerId, PageInfoRequest pageInfoRequest) {
		BakeryFavoriteSortStrategy strategy = bakeryFavoriteSortFactory.getStrategy(pageInfoRequest.sort());
		Pageable pageable = PageRequest.of(pageInfoRequest.page(), pageInfoRequest.size(), strategy.getSort());

		Page<BakeryFavorite> favoritesPage = strategy.getFavoritePage(customerId, pageable);
		return BakeryFavoritesPageResponse.of(favoritesPage);
	}
}
