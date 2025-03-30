package com.breaditnow.customer.domain.product.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.product.controller.res.ProductFavoritePageResponse;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.vo.GeoPoint;

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

