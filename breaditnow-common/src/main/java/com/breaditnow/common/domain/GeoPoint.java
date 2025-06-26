package com.breaditnow.common.domain;

import com.breaditnow.common.exception.BreaditnowException;

import static com.breaditnow.common.exception.CommonErrorCode.*;

public record GeoPoint(
        Double latitude,
        Double longitude
) {
    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LATITUDE = 90.0;
    private static final double MIN_LONGITUDE = -180.0;
    private static final double MAX_LONGITUDE = 180.0;

    public GeoPoint {
        validate(latitude, longitude);
    }

    public static GeoPoint of(Double latitude, Double longitude) {
        if (latitude == null || longitude == null){
            return null;
        }
        return new GeoPoint(latitude, longitude);
    }


    private static void validate(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new BreaditnowException(COORDINATES_REQUIRED);
        }

        validateLatitude(latitude);
        validateLongitude(longitude);
    }

    private static void validateLatitude(double latitude) {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new BreaditnowException(INVALID_LATITUDE_RANGE);
        }

    }

    private static void validateLongitude(double longitude) {
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new BreaditnowException(INVALID_LONGITUDE_RANGE);
        }
    }
}