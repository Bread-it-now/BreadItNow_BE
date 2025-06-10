package com.breaditnow.customer.region.infrastructure;

import com.breaditnow.customer.region.application.port.LoadRegionPort;
import com.breaditnow.customer.region.core.Region;
import com.breaditnow.customer.region.core.RegionId;
import com.breaditnow.customer.region.infrastructure.entity.RegionIdEntity;
import com.breaditnow.customer.region.infrastructure.jpa.JpaRegionRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.REGION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class RegionAdapter implements LoadRegionPort {
    private final JpaRegionRepository jpaRegionRepository;

    @Override
    public Region findById(RegionId id) {
        RegionIdEntity regionIdEntity = new RegionIdEntity(id);
        return jpaRegionRepository.findById(regionIdEntity)
                .orElseThrow(() -> new DomainException(REGION_NOT_FOUND))
                .toRegion();
    }
}
