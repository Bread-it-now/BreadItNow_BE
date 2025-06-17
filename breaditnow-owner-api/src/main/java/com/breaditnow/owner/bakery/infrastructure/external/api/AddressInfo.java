package com.breaditnow.owner.bakery.infrastructure.external.api;

public record AddressInfo(
        String regionCode,
        double latitude,
        double longitude
) {
}
