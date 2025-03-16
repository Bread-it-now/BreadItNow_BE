package com.breaditnow.customer.domain.bakeryfavorite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.util.GeoPoint;
import com.breaditnow.customer.domain.bakeryfavorite.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.domain.domain.favorite.dto.BakeryFavoriteDto;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.CustomerBakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final CustomerBakeryFavoriteRepository customerBakeryFavoriteRepository;

	public BakeryFavoritePageResponse getFavorites(Long customerId, Pageable pageable,
		GeoPointRequest geoPointRequest) {
		GeoPoint currentGeoPoint = geoPointRequest.toEntity();

		Page<BakeryFavoriteDto> pageBakeryFavorites = customerBakeryFavoriteRepository.findBakeryFavorites(customerId,
			pageable,
			currentGeoPoint);

		return BakeryFavoritePageResponse.of(pageBakeryFavorites);
	}
}
