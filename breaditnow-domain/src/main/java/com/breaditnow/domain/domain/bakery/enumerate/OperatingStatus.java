package com.breaditnow.domain.domain.bakery.enumerate;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.domain.global.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum OperatingStatus {
	OPEN, CLOSED, TEMPORARY_CLOSED;

	@JsonCreator
	public static OperatingStatus from(String type) {
		try {
			return OperatingStatus.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new DomainException(BAKERY_OPERATING_NOT_FOUND);
		}
	}
}
