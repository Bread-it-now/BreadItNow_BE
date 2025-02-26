package com.breaditnow.domain.domain.region.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.global.exception.DomainException;

public interface RegionRepository extends JpaRepository<Region, RegionPK> {
	default void checkExists(RegionPK regionPK) {
		if (!existsById(regionPK)) {
			throw new DomainException(REGION_NOT_FOUND);
		}
	}
}
