package com.breaditnow.customer.region.domain.port;

import com.breaditnow.customer.region.domain.Region;

public interface LoadRegionPort {
    boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix);
    Region getRegionByName(String sidoName, String gugunName, String dongName);
}
