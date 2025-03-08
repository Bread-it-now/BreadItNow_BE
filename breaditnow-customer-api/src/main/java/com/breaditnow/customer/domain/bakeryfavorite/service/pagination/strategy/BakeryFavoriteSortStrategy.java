package com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;

public interface BakeryFavoriteSortStrategy {
	Page<BakeryFavorite> getFavoritePage(Long customerId, Pageable pageable);

	Sort getSort();
}
