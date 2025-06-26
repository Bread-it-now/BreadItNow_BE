package com.breaditnow.location.domain.port.out;

import com.breaditnow.location.application.dto.AddressInfo;

public interface AddressPort {
    AddressInfo getAddressInfo(String fullAddress);
}
