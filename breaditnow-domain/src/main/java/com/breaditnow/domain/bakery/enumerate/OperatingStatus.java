package com.breaditnow.domain.bakery.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OperatingStatus {
	OPEN, CLOSED, TEMPORARY_CLOSED;

	@JsonCreator
	public static OperatingStatus from(String type) {
		return OperatingStatus.valueOf(type.toUpperCase());
	}
}
