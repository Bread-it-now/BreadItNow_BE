package com.breaditnow.domain.bakery.repository;

import static com.breaditnow.global.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.bakery.entity.Bakery;
import com.breaditnow.global.DomainException;

import lombok.NonNull;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {

	default Bakery getById(@NonNull Long bakeryId) {
		return findById(bakeryId)
			.orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
	}
}
