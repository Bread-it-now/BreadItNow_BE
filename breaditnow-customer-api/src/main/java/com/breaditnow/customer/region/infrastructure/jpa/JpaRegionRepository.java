package com.breaditnow.customer.region.infrastructure.jpa;

import com.breaditnow.customer.region.infrastructure.entity.RegionEntity;
import com.breaditnow.customer.region.infrastructure.entity.RegionIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRegionRepository extends JpaRepository<RegionEntity, RegionIdEntity> {

}
