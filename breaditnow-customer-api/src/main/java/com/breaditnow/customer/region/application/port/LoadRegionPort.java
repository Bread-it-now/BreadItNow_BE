package com.breaditnow.customer.region.application.port;

import com.breaditnow.customer.region.core.Region;
import com.breaditnow.customer.region.core.RegionId;

public interface LoadRegionPort {
    Region findById(RegionId regionId);
}
