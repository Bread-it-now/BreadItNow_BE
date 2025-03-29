package com.breaditnow.domain.domain.bakery.enumerate;

public enum BakerySortType {
	RESERVATION,
	LIKE;

	public static BakerySortType from(String value) {
		try {
			return BakerySortType.valueOf(value.toUpperCase());
		} catch (Exception e) {
			return RESERVATION;
		}
	}
}
