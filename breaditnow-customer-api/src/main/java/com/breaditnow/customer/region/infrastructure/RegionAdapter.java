package com.breaditnow.customer.region.infrastructure;

import com.breaditnow.customer.region.application.port.LoadRegionPort;
import com.breaditnow.customer.region.core.Region;
import com.breaditnow.customer.region.infrastructure.entity.RegionEntity;
import com.breaditnow.customer.region.infrastructure.jpa.JpaRegionRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.domain.global.exception.DomainErrorCode.REGION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class RegionAdapter implements LoadRegionPort {
    private final JpaRegionRepository jpaRegionRepository;

    @Override
    public Region getRegion(String regionCode) {
        return jpaRegionRepository.findById(regionCode)
                .orElseThrow(() -> new DomainException(REGION_NOT_FOUND))
                .toDomain();
    }

    @Override
    public List<Region> findBySidoCode(String sidoCodePrefix) {
        return jpaRegionRepository.findByRegionCodeStartingWith(sidoCodePrefix).stream()
                .map(RegionEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Region> findBySidoAndGugunCode(String sidoAndGugunCodePrefix) {
        return jpaRegionRepository.findByRegionCodeStartingWith(sidoAndGugunCodePrefix)
                .stream()
                .map(RegionEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix) {
        return jpaRegionRepository.existsByRegionCodeStartingWith(sidoAndGugunCodePrefix);
    }
}
