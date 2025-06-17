package com.breaditnow.owner.bakery.application.port.out;

import com.breaditnow.owner.bakery.infrastructure.external.api.AddressInfo;

public interface AddressPort {
    AddressInfo getAddressInfo(String fullAddress);
}
