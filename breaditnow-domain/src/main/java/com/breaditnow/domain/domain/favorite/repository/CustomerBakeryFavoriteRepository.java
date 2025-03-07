package com.breaditnow.domain.domain.favorite.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;
import com.breaditnow.domain.global.exception.DomainException;

public interface CustomerBakeryFavoriteRepository extends JpaRepository<CustomerBakeryFavorite, Long> {
	default CustomerBakeryFavorite getByCustomerIdAndBakeryId(Long customerId, Long bakeryId) {
		return findByCustomerIdAndBakeryId(customerId, bakeryId)
			.orElseThrow(() -> new DomainException(BAKERY_FAVORITE_NOT_FOUND));
	}

	Optional<CustomerBakeryFavorite> findByCustomerIdAndBakeryId(Long customerId, Long bakeryId);
}
