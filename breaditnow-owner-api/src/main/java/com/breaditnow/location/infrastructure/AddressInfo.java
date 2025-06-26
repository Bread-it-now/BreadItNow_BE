package com.breaditnow.location.infrastructure;

public record AddressInfo(
        String regionCode,
        double latitude,
        double longitude
) {
}
