package com.breaditnow.customer.domain.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.res.SearchBakeryPageResponse;
import com.breaditnow.customer.domain.search.controller.request.SearchRequest;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	private final BakeryRepository bakeryRepository;

	public SearchBakeryPageResponse searchBakeries(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());

		Page<BakeryDistanceDto> bakeryDistanceDtos = bakeryRepository.searchBakeriesWithKeyword(customerId, pageable,
			searchRequest.sort(), searchRequest.keyword(),
			geoPoint);

		return null;
	}
}
