package com.breaditnow.location.application.port.out;

import com.breaditnow.location.infrastructure.AddressInfo;

public interface AddressPort {
    AddressInfo getAddressInfo(String fullAddress);
}
