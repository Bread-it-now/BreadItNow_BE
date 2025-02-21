package com.breaditnow.domain.region.repository;

import static com.breaditnow.global.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.region.entity.Region;
import com.breaditnow.domain.region.entity.RegionPK;
import com.breaditnow.global.DomainException;

import lombok.NonNull;

public interface RegionRepository extends JpaRepository<Region, RegionPK> {
	default Region getById(@NonNull RegionPK regionPK) {
		return findById(regionPK)
			.orElseThrow(() -> new DomainException(Region_NOT_FOUND));
	}
}
