package com.breaditnow.customer.location.application.port.out;

import com.breaditnow.customer.location.application.AddressInfo;

import java.util.Optional;

public interface AddressPort {
    Optional<AddressInfo> getAddressInfo(double longitude, double latitude);
}
