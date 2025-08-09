package com.breaditnow.customer.domain.port.out;

import com.breaditnow.customer.domain.model.Region;

public interface RegionRepository {
    boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix);
    Region getRegionByName(String sidoName, String gugunName, String dongName);
}
