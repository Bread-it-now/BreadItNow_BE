package com.breaditnow.customer.domain.bakeryfavorite.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.CustomerBakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoritePageService {
	private final CustomerBakeryFavoriteRepository customerBakeryFavoriteRepository;

	public BakeryFavoritePageResponse getFavorites(Long customerId, Pageable pageable) {
		return BakeryFavoritePageResponse.of(
			customerBakeryFavoriteRepository.findBakeryFavorites(customerId, pageable));
	}
}
