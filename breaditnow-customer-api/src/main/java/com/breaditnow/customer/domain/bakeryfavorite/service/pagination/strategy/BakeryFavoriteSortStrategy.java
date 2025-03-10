package com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;

public interface BakeryFavoriteSortStrategy {
	Page<CustomerBakeryFavorite> getFavoritePage(Long customerId, Pageable pageable);

	Sort getSort();
}
