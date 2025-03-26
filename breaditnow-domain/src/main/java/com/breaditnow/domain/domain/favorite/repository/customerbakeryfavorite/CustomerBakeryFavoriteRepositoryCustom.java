package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.favorite.dto.BakeryFavoriteDto;
import com.breaditnow.domain.domain.vo.GeoPoint;

public interface CustomerBakeryFavoriteRepositoryCustom {
	Page<BakeryFavoriteDto> findBakeryFavorites(Long customerId, Pageable pageable, GeoPoint geoPoint);
}
