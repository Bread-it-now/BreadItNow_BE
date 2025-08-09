package com.breaditnow.region.domain.port.out;

import com.breaditnow.customer.application.dto.AddressInfo;
import com.breaditnow.customer.application.dto.response.GugunResponse;
import com.breaditnow.customer.application.dto.response.SidoResponse;
import com.breaditnow.region.domain.model.Region;

import java.util.List;

public interface RegionRepository {
    boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix);
    Region getRegionByName(AddressInfo addressInfo);

    List<SidoResponse> findSidoResponses();
    List<GugunResponse> findGugunResponsesBySidoCode(String sidoCode);
}
