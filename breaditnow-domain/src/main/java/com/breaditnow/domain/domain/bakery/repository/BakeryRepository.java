package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.global.exception.DomainException;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {
	default Bakery getById(Long id) {
		Bakery bakery = findById(id)
			.orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
		validateActive(bakery);
		return bakery;
	}

	default Bakery getByOwnerIdAndId(Long ownerId, Long id) {
		Bakery bakery = getById(id);
		if (!bakery.getOwner().getId().equals(ownerId)) {
			throw new DomainException(OWNER_MISMATCH);
		}
		return bakery;
	}

	private void validateActive(Bakery bakery) {
		if (!bakery.isActive()) {
			throw new DomainException(BAKERY_INACTIVE);
		}
	}

	default void checkDuplicateOwner(Long ownerId) {
		if (existsByOwnerIdAndIsActiveTrue(ownerId)) {
			throw new DomainException(DUPLICATE_OWNER_BAKERY);
		}
	}

	boolean existsByOwnerIdAndIsActiveTrue(Long ownerId);
}
