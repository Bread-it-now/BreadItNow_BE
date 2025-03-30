package com.breaditnow.domain.domain.bakery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.dto.BakeryDistanceDto;
import com.breaditnow.domain.domain.vo.GeoPoint;

public interface BakeryRepositoryCustom {
	Page<BakeryDistanceDto> searchHotBakeries(Long customerId, String sort, Pageable pageable, GeoPoint geoPoint);
}
