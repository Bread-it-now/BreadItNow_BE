package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

public interface CustomerBakeryFavoriteRepositoryCustom {
	Page<Bakery> findBakeryFavorites(Long customerId, Pageable pageable);
}
