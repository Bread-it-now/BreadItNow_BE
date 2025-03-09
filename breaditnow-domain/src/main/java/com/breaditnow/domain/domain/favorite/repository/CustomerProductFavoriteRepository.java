package com.breaditnow.domain.domain.favorite.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.favorite.entity.CustomerProductFavorite;
import com.breaditnow.domain.global.exception.DomainException;

public interface CustomerProductFavoriteRepository extends JpaRepository<CustomerProductFavorite, Long> {
	default CustomerProductFavorite getByCustomerIdAndProductId(Long customerId, Long productId) {
		return findByCustomerIdAndProductId(customerId, productId)
			.orElseThrow(() -> new DomainException(BREAD_FAVORITE_NOT_FOUND));
	}

	Optional<CustomerProductFavorite> findByCustomerIdAndProductId(Long customerId, Long productId);
}
