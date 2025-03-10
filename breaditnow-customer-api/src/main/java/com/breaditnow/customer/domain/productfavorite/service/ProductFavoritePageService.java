package com.breaditnow.customer.domain.productfavorite.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.productfavorite.controller.res.ProductFavoritesPageResponse;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFavoritePageService {
	private final CustomerProductFavoriteRepository customerProductFavoriteRepository;

	public ProductFavoritesPageResponse getFavorites(Long customerId, int page, int size, String sortType) {
		PageRequest pageable = PageRequest.of(page, size);

		return ProductFavoritesPageResponse.of(
			customerProductFavoriteRepository.findProductFavorites(customerId, pageable, sortType));
	}
}

