package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.region.core.Region;

import java.util.List;

public interface SaveCustomerRegionPort {
    void preference(Customer customer, Region regions);

    void preferenceAll(Customer customer, List<Region> region);
}
