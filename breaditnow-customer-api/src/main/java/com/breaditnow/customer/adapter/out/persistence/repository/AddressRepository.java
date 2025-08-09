package com.breaditnow.customer.adapter.out.persistence.repository;

import com.breaditnow.customer.application.dto.AddressInfo;

import java.util.Optional;

public interface AddressRepository {
    Optional<AddressInfo> getAddressInfo(double longitude, double latitude);
}
