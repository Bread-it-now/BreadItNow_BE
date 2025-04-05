package com.breaditnow.domain.domain.bakery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

public interface BakeryRepositoryCustom {
	Page<BakeryDistanceDto> searchHotBakeriesByFavorite(Long customerId, Pageable pageable, GeoPoint geoPoint);

	Page<BakeryDistanceDto> searchHotBakeriesByReservation(Long customerId, Pageable pageable, GeoPoint geoPoint);
}
