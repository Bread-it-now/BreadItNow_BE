package com.breaditnow.customer.domain.bakery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.CustomerBakeryFavoriteRepository;
import com.breaditnow.domain.global.dto.BakeryFavoriteDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final CustomerBakeryFavoriteRepository customerBakeryFavoriteRepository;

	public BakeryFavoritePageResponse getFavorites(Long customerId, int page, int size, String sort,
		GeoPointRequest geoPointRequest) {
		GeoPoint currentGeoPoint = geoPointRequest.toEntity();
		Pageable pageable = PageRequest.of(page, size);

		Page<BakeryFavoriteDistanceDto> pageBakeryFavorites = customerBakeryFavoriteRepository.findBakeryFavorites(
			customerId, pageable, sort, currentGeoPoint);

		return BakeryFavoritePageResponse.of(pageBakeryFavorites);
	}
}
