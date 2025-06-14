package com.breaditnow.customer.region.application.port;

import com.breaditnow.customer.region.core.Region;

import java.util.List;

public interface LoadRegionPort {
    Region getRegion(String regionCode);
    List<Region> findBySidoCode(String sidoCodePrefix);
    List<Region> findBySidoAndGugunCode(String sidoAndGugunCodePrefix);
    boolean existsBySidoAndGugunCode(String sidoAndGugunCodePrefix);
}
