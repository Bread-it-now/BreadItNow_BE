package com.breaditnow.customer.region.infrastructure;

import com.breaditnow.customer.region.domain.port.LoadRegionPort;
import com.breaditnow.customer.region.domain.Region;
import com.breaditnow.customer.region.infrastructure.entity.RegionEntity;
import com.breaditnow.customer.region.infrastructure.jpa.JpaRegionRepository;
import com.breaditnow.customer.region.presentation.res.GugunResponse;
import com.breaditnow.customer.region.presentation.res.SidoResponse;
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

    @Override
    public Region getRegionByName(String sidoName, String gugunName, String dongName) {
        return jpaRegionRepository.findBySidoNameAndGugunNameAndDongName(sidoName, gugunName, dongName)
                .orElseThrow(() -> new DomainException(REGION_NOT_FOUND))
                .toDomain();
    }

    public List<SidoResponse> findDistinctSidoInfoGroupedBySidoCodeAndName() {
        return jpaRegionRepository.findDistinctSidoInfoGroupedBySidoCodeAndName();
    }

    public List<GugunResponse> findDistinctGugunResponsesBySidoCode(String sidoCode){
        return jpaRegionRepository.findDistinctGugunResponsesBySidoCode(sidoCode);
    }
}
