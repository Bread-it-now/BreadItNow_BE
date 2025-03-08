package com.breaditnow.domain.domain.favorite.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;
import com.breaditnow.domain.global.exception.DomainException;

public interface FavoriteRepository extends JpaRepository<BakeryFavorite, Long> {
	default BakeryFavorite getByCustomerIdAndBakeryId(Long customerId, Long bakeryId) {
		return findByCustomerIdAndBakeryId(customerId, bakeryId)
			.orElseThrow(() -> new DomainException(BAKERY_FAVORITE_NOT_FOUND));
	}

	Optional<BakeryFavorite> findByCustomerIdAndBakeryId(Long customerId, Long bakeryId);

	Page<BakeryFavorite> findAllByCustomerIdAndIsActiveTrue(Long customerId, Pageable pageable);
}
