package com.breaditnow.owner.bakery.domain;

import com.breaditnow.owner.global.exception.OwnerException;
import io.micrometer.common.util.StringUtils;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;


public record Address(
        String regionCode,
        String fullAddress,
        double latitude,
        double longitude
) {
    public Address {
        if(StringUtils.isEmpty(regionCode)){
            throw new OwnerException(REGION_CODE_REQUIRED);
        }
        if(StringUtils.isEmpty(fullAddress)){
            throw new OwnerException(FULL_ADDRESS_REQUIRED);
        }
        if (latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude > 180.0) {
            throw new OwnerException(GEOLOCATION_NOT_FOUND);
        }
    }

    public static Address create(String regionCode, String fullAddress, double latitude, double longitude) {
        return new Address(regionCode, fullAddress, latitude, longitude);
    }
}
