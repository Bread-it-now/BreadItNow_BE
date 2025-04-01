package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.global.exception.DomainException;

public interface BakeryRepository extends JpaRepository<Bakery, Long>, BakeryRepositoryCustom {
	boolean existsByOwnerIdAndIsActiveTrue(Long ownerId);

	Optional<Bakery> findByOwnerIdAndIsActiveTrue(Long ownerId);

	default void checkBakeryIsAlive(Long id) {
		getByIdAndIsActiveTrue(id);
	}

	default Bakery getById(Long id) {
		return findById(id)
			.orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
	}

	default Bakery getByIdAndIsActiveTrue(Long id) {
		Bakery bakery = getById(id);
		if (!bakery.isActive()) {
			throw new DomainException(BAKERY_INACTIVE);
		}
		return bakery;
	}

	default Bakery getByOwnerIdAndId(Long ownerId, Long id) {
		Bakery bakery = getByIdAndIsActiveTrue(id);
		if (!bakery.getOwner().getId().equals(ownerId)) {
			throw new DomainException(OWNER_MISMATCH);
		}
		return bakery;
	}

	default void checkDuplicateOwner(Long ownerId) {
		if (existsByOwnerIdAndIsActiveTrue(ownerId)) {
			throw new DomainException(DUPLICATE_OWNER_BAKERY);
		}
	}
}
