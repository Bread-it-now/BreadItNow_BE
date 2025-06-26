package com.breaditnow.owner.reservation.application.port.out;

import com.breaditnow.owner.reservation.domain.CustomerProfile;

import java.util.List;
import java.util.Map;

public interface CustomerProfileProviderPort {
    Map<Long, CustomerProfile> getCustomerProfileMap(List<Long> customerIds);
}
