package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.favorite.dto.BakeryFavoriteDistanceDto;
import com.breaditnow.domain.domain.vo.GeoPoint;

public interface CustomerBakeryFavoriteRepositoryCustom {
	Page<BakeryFavoriteDistanceDto> findBakeryFavorites(Long customerId, Pageable pageable, String sort,
		GeoPoint geoPoint);
}
