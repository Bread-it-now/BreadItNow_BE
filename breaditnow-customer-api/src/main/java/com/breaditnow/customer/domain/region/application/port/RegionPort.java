package com.breaditnow.customer.domain.region.application.port;

import com.breaditnow.customer.domain.region.core.Region;
import com.breaditnow.customer.domain.region.core.RegionId;

public interface RegionPort {
    Region findById(RegionId regionId);
}
