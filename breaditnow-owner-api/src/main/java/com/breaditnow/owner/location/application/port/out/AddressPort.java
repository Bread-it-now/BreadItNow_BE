package com.breaditnow.owner.location.application.port.out;

import com.breaditnow.owner.location.infrastructure.AddressInfo;

public interface AddressPort {
    AddressInfo getAddressInfo(String fullAddress);
}
