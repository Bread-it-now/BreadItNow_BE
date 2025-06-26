package com.breaditnow.location.application.dto;

public record AddressInfo(
        String regionCode,
        double latitude,
        double longitude
) {
}
