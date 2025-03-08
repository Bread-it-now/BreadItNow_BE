package com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.BakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PopularBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	private final BakeryFavoriteRepository repository;

	@Override
	public Page<BakeryFavorite> getFavoritePage(Long customerId,
		Pageable pageable) {
		return repository.findFavoriteBakeryGroupedByOwnerOrderByCount(customerId, pageable);
	}

	@Override
	public Sort getSort() {
		return Sort.unsorted();
	}
}
