package com.breaditnow.customer.domain.productfavorite.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.util.GeoPoint;
import com.breaditnow.customer.domain.bakeryfavorite.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.productfavorite.controller.res.ProductFavoritePageResponse;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFavoritePageService {
	private final CustomerProductFavoriteRepository customerProductFavoriteRepository;

	public ProductFavoritePageResponse getFavorites(Long customerId, Pageable pageable,
		GeoPointRequest geoPointRequest) {
		GeoPoint currentGeoPoint = geoPointRequest.toEntity();

		return ProductFavoritePageResponse.of(
			customerProductFavoriteRepository.findProductFavorites(customerId, pageable, currentGeoPoint));
	}
}

