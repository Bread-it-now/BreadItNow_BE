package com.breaditnow.common.util.geodistance;

import static java.lang.Math.*;

import java.util.Optional;

public class GeoDistanceUtil {
	private static final double EARTH_RADIUS_KM = 6371.0;

	public static Optional<Double> calculateDistance(GeoPoint o1, GeoPoint o2) {
		if (o1 == null || o2 == null) {
			return Optional.empty();
		}

		double deltaLatitude = toRadians(o2.latitude() - o1.latitude());
		double deltaLongitude = toRadians(o2.longitude() - o1.longitude());

		double haversineValue = sin(deltaLatitude / 2) * sin(deltaLatitude / 2)
			+ cos(toRadians(o1.latitude())) * cos(toRadians(o2.latitude()))
			* sin(deltaLongitude / 2) * sin(deltaLongitude / 2);

		double centralAngle = 2 * atan2(sqrt(haversineValue), sqrt(1 - haversineValue));

		return Optional.of(EARTH_RADIUS_KM * centralAngle);
	}
}
