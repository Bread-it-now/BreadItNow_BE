package com.breaditnow.reservation.infrastructure.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class ReservationException extends BreaditnowException {

	public ReservationException(ReservationErrorCode errorCode) {
		super(errorCode);
	}

	public ReservationException(ReservationErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

