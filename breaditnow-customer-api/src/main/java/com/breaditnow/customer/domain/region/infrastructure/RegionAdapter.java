package com.breaditnow.customer.domain.region.infrastructure;

import com.breaditnow.customer.domain.region.application.port.RegionPort;
import com.breaditnow.customer.domain.region.core.Region;
import com.breaditnow.customer.domain.region.core.RegionId;
import com.breaditnow.customer.domain.region.infrastructure.entity.RegionIdEntity;
import com.breaditnow.customer.domain.region.infrastructure.jpa.JpaRegionRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.REGION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class RegionAdapter implements RegionPort {
    private final JpaRegionRepository jpaRegionRepository;

    @Override
    public Region findById(RegionId id) {
        RegionIdEntity regionIdEntity = new RegionIdEntity(id);
        return jpaRegionRepository.findById(regionIdEntity)
                .orElseThrow(() -> new DomainException(REGION_NOT_FOUND))
                .toRegion();
    }
}
