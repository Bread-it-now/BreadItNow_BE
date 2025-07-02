package com.breaditnow.common.exception;

public class ReservationException extends BreaditnowException {

	public ReservationException(ReservationErrorCode errorCode) {
		super(errorCode);
	}

	public ReservationException(ReservationErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

