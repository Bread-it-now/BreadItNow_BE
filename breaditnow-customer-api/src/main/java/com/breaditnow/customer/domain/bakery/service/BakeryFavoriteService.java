package com.breaditnow.customer.domain.bakery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.CustomerBakeryFavoriteRepository;

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
		Customer customer = customerRepository.getById(customerId);
		Bakery bakery = bakeryRepository.getById(bakeryId);

		CustomerBakeryFavorite customerBakeryFavorite = CustomerBakeryFavorite.builder()
			.customer(customer)
			.bakery(bakery)
			.build();

		CustomerBakeryFavorite savedCustomerBakeryFavorite = customerBakeryFavoriteRepository.save(
			customerBakeryFavorite);

		return savedCustomerBakeryFavorite.getId();
	}
}
