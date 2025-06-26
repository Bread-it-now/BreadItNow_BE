package com.breaditnow.customer.region.infrastructure;

import com.breaditnow.customer.common.exception.CustomerErrorCode;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.region.domain.Region;
import com.breaditnow.customer.region.domain.port.LoadRegionPort;
import com.breaditnow.customer.region.infrastructure.jpa.JpaRegionRepository;
import com.breaditnow.customer.region.presentation.res.GugunResponse;
import com.breaditnow.customer.region.presentation.res.SidoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class RegionAdapter implements LoadRegionPort {
    private final JpaRegionRepository jpaRegionRepository;
    @Override
    public boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix) {
        return jpaRegionRepository.existsByRegionCodeStartingWith(sidoAndGugunCodePrefix);
    }

    @Override
    public Region getRegionByName(String sidoName, String gugunName, String dongName) {
        return jpaRegionRepository.findBySidoNameAndGugunNameAndDongName(sidoName, gugunName, dongName)
                .orElseThrow(() -> new CustomerException(CustomerErrorCode.REGION_NOT_FOUND))
                .toDomain();
    }

    public List<SidoResponse> findSidoResponses() {
        return jpaRegionRepository.findSidoResponses();
    }

    public List<GugunResponse> findGugunResponsesBySidoCode(String sidoCode){
        return jpaRegionRepository.findGugunResponsesBySidoCode(sidoCode);
    }
}
