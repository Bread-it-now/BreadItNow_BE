package com.breaditnow.owner.location.infrastructure;

public record AddressInfo(
        String regionCode,
        double latitude,
        double longitude
) {
}
