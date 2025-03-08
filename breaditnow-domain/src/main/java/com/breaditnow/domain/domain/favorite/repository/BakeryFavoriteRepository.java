package com.breaditnow.domain.domain.favorite.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;
import com.breaditnow.domain.global.exception.DomainException;

public interface BakeryFavoriteRepository extends JpaRepository<BakeryFavorite, Long> {
	default BakeryFavorite getByCustomerIdAndBakeryId(Long customerId, Long bakeryId) {
		return findByCustomerIdAndBakeryId(customerId, bakeryId)
			.orElseThrow(() -> new DomainException(BAKERY_FAVORITE_NOT_FOUND));
	}

	Optional<BakeryFavorite> findByCustomerIdAndBakeryId(Long customerId, Long bakeryId);

	Page<BakeryFavorite> findAllByCustomerIdAndIsActiveTrue(Long customerId, Pageable pageable);

	@Query(
		"SELECT bf FROM BakeryFavorite bf " +
			"WHERE bf.customer.id = :customerId " +
			"  AND bf.isActive = true " +
			"  AND bf.id IN (SELECT MIN(bf2.id) " +
			"                FROM BakeryFavorite bf2 " +
			"                WHERE bf2.customer.id = :customerId " +
			"                  AND bf2.isActive = true " +
			"                GROUP BY bf2.bakery.owner.id) " +
			"ORDER BY (SELECT COUNT(bf3) " +
			"          FROM BakeryFavorite bf3 " +
			"          WHERE bf3.bakery.owner.id = bf.bakery.owner.id " +
			"            AND bf3.isActive = true) DESC"
	)
	Page<BakeryFavorite> findFavoriteBakeryGroupedByOwnerOrderByCount(@Param("customerId") Long customerId,
		Pageable pageable);

}
