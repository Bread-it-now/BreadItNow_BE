package com.breaditnow.customer.region.infrastructure.jpa;

import com.breaditnow.customer.region.infrastructure.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRegionRepository extends JpaRepository<RegionEntity, String> {
    Optional<RegionEntity> findRegionEntityByRegionCode(String regionCode);
    List<RegionEntity> findByRegionCodeStartingWith(String regionCodePrefix);
    boolean existsByRegionCodeStartingWith(String regionCodePrefix);
}
