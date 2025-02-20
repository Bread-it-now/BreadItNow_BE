package com.breaditnow.domain.owner.repository;

import static com.breaditnow.global.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.global.DomainException;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
	default Owner getById(Long ownerId) {
		return findById(ownerId)
			.orElseThrow(() -> new DomainException(OWNER_NOT_FOUND));
	}
}
