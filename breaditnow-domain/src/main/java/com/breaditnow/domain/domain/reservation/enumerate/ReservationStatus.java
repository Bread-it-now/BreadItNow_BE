package com.breaditnow.domain.domain.reservation.enumerate;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.domain.global.exception.DomainException;

public enum ReservationStatus {
	WAITING, APPROVED, PARTIAL_APPROVED, CANCELLED, PAYMENT_COMPLETED;

	public static ReservationStatus from(String status) {
		try {
			return ReservationStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new DomainException(INVALID_RESERVATION_STATUS_TYPE);
		}
	}
}
