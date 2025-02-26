package com.breaditnow.domain.bakery.repository;

import static com.breaditnow.global.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.bakery.entity.Bakery;
import com.breaditnow.global.DomainException;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {
	default Bakery getById(Long id) {
		Bakery bakery = findById(id)
			.orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));

		if (!bakery.isActive()) {
			throw new DomainException(BAKERY_INACTIVE);
		}

		return bakery;
	}

	default Bakery getByOwnerIdAndId(Long ownerId, Long id) {
		Bakery bakery = findById(id)
			.orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));

		if (!bakery.isActive()) {
			throw new DomainException(BAKERY_INACTIVE);
		}

		if (!bakery.getOwner().getId().equals(ownerId)) {
			throw new DomainException(OWNER_MISMATCH);
		}
		return bakery;
	}
}
