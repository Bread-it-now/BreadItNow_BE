package com.breaditnow.owner.bakery.infrastructure.external.api;

public interface AddressPort {
    AddressInfo getAddressInfo(String fullAddress);
}
