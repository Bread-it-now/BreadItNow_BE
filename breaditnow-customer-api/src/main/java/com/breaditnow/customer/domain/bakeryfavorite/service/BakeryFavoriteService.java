package com.breaditnow.customer.domain.bakeryfavorite.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesPageResponse;
import com.breaditnow.customer.domain.bakeryfavorite.controller.res.BakeryFavoritesResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.FavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoriteService {
	private final FavoriteRepository favoriteRepository;
	private final CustomerRepository customerRepository;
	private final BakeryRepository bakeryRepository;

	@Transactional
	public Long likeBakery(Long customerId, Long bakeryId) {
		return favoriteRepository.findByCustomerIdAndBakeryId(customerId, bakeryId)
			.map(favorite -> {
				favorite.changeActive(true);
				return favorite.getId();
			})
			.orElseGet(() -> {
				Customer customer = customerRepository.getById(customerId);
				Bakery bakery = bakeryRepository.getById(bakeryId);

				BakeryFavorite newFavorite = BakeryFavorite.builder()
					.customer(customer)
					.bakery(bakery)
					.build();

				return favoriteRepository.save(newFavorite).getId();
			});
	}

	@Transactional
	public Long deleteBakery(Long customerId, Long bakeryId) {
		BakeryFavorite bakeryFavorite = favoriteRepository.getByCustomerIdAndBakeryId(
			customerId, bakeryId);

		bakeryFavorite.changeActive(false);

		return bakeryFavorite.getId();
	}

	public BakeryFavoritesPageResponse getFavorites(Long customerId, Pageable pageable) {
		Page<BakeryFavorite> favoritesPage = favoriteRepository.findAllByCustomerIdAndIsActiveTrue(customerId,
			pageable);

		List<BakeryFavoritesResponse> favoriteItems = favoritesPage.getContent().stream()
			.map(favorite -> new BakeryFavoritesResponse(
				favorite.getBakery().getId(),
				favorite.getBakery().getName(),
				favorite.getBakery().getProfileImage(),
				0
			))
			.toList();

		PageInfo pageInfo = PageInfo.of(favoritesPage.getTotalElements(), favoritesPage.getTotalPages(),
			favoritesPage.isLast(), favoritesPage.getPageable().getPageNumber());
		return BakeryFavoritesPageResponse.of(favoriteItems, pageInfo);
	}
}
