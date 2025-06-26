package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.domain.CustomerProfile;

import java.util.List;
import java.util.Map;

public interface CustomerProfileProviderPort {
    Map<Long, CustomerProfile> getCustomerProfileMap(List<Long> customerIds);
}
