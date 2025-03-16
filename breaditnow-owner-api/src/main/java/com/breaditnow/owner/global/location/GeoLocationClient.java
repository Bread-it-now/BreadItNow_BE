package com.breaditnow.owner.global.location;

public interface GeoLocationClient {
	AddressCoordinate lookupCoordinates(String address);
}
