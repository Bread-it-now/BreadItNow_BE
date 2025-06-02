package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.region.core.Region;

public interface LoadCustomerRegionPort {
    boolean checkPreference(Customer customer, Region region);
}
