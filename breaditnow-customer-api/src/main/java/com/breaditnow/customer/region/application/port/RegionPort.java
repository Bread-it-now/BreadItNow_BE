package com.breaditnow.customer.region.application.port;

import com.breaditnow.customer.region.core.Region;
import com.breaditnow.customer.region.core.RegionId;

public interface RegionPort {
    Region findById(RegionId regionId);
}
