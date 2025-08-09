package com.breaditnow.region.adapter.out.persistence;

import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.adapter.out.persistence.repository.JpaRegionRepository;
import com.breaditnow.customer.application.dto.AddressInfo;
import com.breaditnow.customer.application.dto.response.GugunResponse;
import com.breaditnow.customer.application.dto.response.SidoResponse;
import com.breaditnow.region.domain.model.Region;
import com.breaditnow.region.domain.port.out.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.breaditnow.common.exception.CustomerErrorCode.REGION_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class RegionAdapter implements RegionRepository {
    private final JpaRegionRepository jpaRegionRepository;

    @Override
    public boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix) {
        return jpaRegionRepository.existsByRegionCodeStartingWith(sidoAndGugunCodePrefix);
    }

    @Override
    public Region getRegionByName(AddressInfo addressInfo) {
        return jpaRegionRepository.findBySidoNameAndGugunNameAndDongName(addressInfo.getSidoName(), addressInfo.getGugunName(), addressInfo.getDongName())
                .orElseThrow(() -> new CustomerException(REGION_NOT_FOUND))
                .toDomain();
    }

    @Override
    public List<SidoResponse> findSidoResponses() {
        return jpaRegionRepository.findSidoResponses();
    }

    @Override
    public List<GugunResponse> findGugunResponsesBySidoCode(String sidoCode){
        return jpaRegionRepository.findGugunResponsesBySidoCode(sidoCode);
    }
}
