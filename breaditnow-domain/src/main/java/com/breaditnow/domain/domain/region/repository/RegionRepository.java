package com.breaditnow.domain.domain.region.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.global.exception.DomainException;

import lombok.NonNull;

public interface RegionRepository extends JpaRepository<Region, RegionPK> {
	default Region getById(@NonNull RegionPK regionPK) {
		return findById(regionPK)
			.orElseThrow(() -> new DomainException(Region_NOT_FOUND));
	}
}
