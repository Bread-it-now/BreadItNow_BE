package com.breaditnow.customer.domain.bakery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryPageService {
	private final BakeryRepository bakeryRepository;

	public HotBakeryPageResponse searchHotBakeries(Long customerId, int page, int size, String sort,
		GeoPointRequest geoPointRequest) {
		GeoPoint currentGeoPoint = geoPointRequest.toEntity();
		Pageable pageable = PageRequest.of(page, size);

		BakerySortStrategy bakerySortStrategy = BakerySortStrategy.from(sort);
		Page<BakeryDistanceDto> bakeryDistances = bakerySortStrategy.search(customerId, pageable, currentGeoPoint,
			bakeryRepository);

		return HotBakeryPageResponse.of(bakeryDistances);
	}
}
