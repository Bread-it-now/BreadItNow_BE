package com.breaditnow.customer.domain.bakeryfavorite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.util.geodistance.GeoDistanceUtil;
import com.breaditnow.common.util.geodistance.GeoPoint;
import com.breaditnow.customer.domain.bakeryfavorite.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoriteResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.CustomerBakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final CustomerBakeryFavoriteRepository customerBakeryFavoriteRepository;

	public BakeryFavoritePageResponse getFavorites(Long customerId, Pageable pageable,
		GeoPointRequest geoPointRequest) {
		Page<Bakery> pageBakeryFavorites = customerBakeryFavoriteRepository.findBakeryFavorites(customerId, pageable);

		GeoPoint currentGeoPoint = geoPointRequest.toEntity();

		List<BakeryFavoriteResponse> bakeryFavoriteResponses = pageBakeryFavorites.stream()
			.map(bakery -> {
				GeoPoint bakeryGeoPoint = new GeoPoint(bakery.getAddress().getLatitude(), bakery.getAddress()
					.getLongitude());
				Optional<Double> distanceOptional = GeoDistanceUtil.calculateDistance(currentGeoPoint, bakeryGeoPoint);
				return BakeryFavoriteResponse.of(bakery, distanceOptional.orElse(null));
			})
			.toList();

		return BakeryFavoritePageResponse.of(bakeryFavoriteResponses, pageBakeryFavorites);
	}
}
