package com.breaditnow.customer.domain.customer.core.port;

import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.region.core.Region;

import java.util.List;

public interface CustomerRegionPort {
    void preference(Customer customer, Region regions);

    void preferenceAll(Customer customer, List<Region> region);

    boolean checkPreference(Customer customer, Region region);
}
