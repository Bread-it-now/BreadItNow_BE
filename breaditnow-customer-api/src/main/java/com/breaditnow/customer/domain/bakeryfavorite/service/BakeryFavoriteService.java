package com.breaditnow.customer.domain.bakeryfavorite.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.CustomerBakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryFavoriteService {
	private final CustomerBakeryFavoriteRepository customerBakeryFavoriteRepository;
	private final CustomerRepository customerRepository;
	private final BakeryRepository bakeryRepository;

	@Transactional
	public Long likeBakery(Long customerId, Long bakeryId) {
		bakeryRepository.checkBakeryIsAlive(bakeryId);

		return customerBakeryFavoriteRepository.findByCustomerIdAndBakeryId(customerId, bakeryId)
			.map(favorite -> {
				favorite.changeActive(true);
				return favorite.getId();
			})
			.orElseGet(() -> {
				Customer customer = customerRepository.getById(customerId);
				Bakery bakery = bakeryRepository.getById(bakeryId);

				CustomerBakeryFavorite newFavorite = CustomerBakeryFavorite.builder()
					.customer(customer)
					.bakery(bakery)
					.build();

				return customerBakeryFavoriteRepository.save(newFavorite).getId();
			});
	}

	@Transactional
	public Long deleteBakery(Long customerId, Long bakeryId) {
		bakeryRepository.checkBakeryIsAlive(bakeryId);
		CustomerBakeryFavorite customerBakeryFavorite = customerBakeryFavoriteRepository.getByCustomerIdAndBakeryId(
			customerId, bakeryId);

		customerBakeryFavorite.changeActive(false);

		return customerBakeryFavorite.getId();
	}
}
