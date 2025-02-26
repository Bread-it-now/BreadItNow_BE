package com.breaditnow.domain.domain.owner.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.global.exception.DomainException;

import lombok.NonNull;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
	default Owner getById(@NonNull Long ownerId) {
		return findById(ownerId)
			.orElseThrow(() -> new DomainException(OWNER_NOT_FOUND));
	}
}
