package com.breaditnow.customer.adapter.out.persistence;

import com.breaditnow.common.exception.CustomerErrorCode;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.domain.model.Region;
import com.breaditnow.customer.domain.port.out.RegionRepository;
import com.breaditnow.customer.adapter.out.persistence.repository.JpaRegionRepository;
import com.breaditnow.customer.application.dto.response.GugunResponse;
import com.breaditnow.customer.application.dto.response.SidoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class RegionAdapter implements RegionRepository {
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
