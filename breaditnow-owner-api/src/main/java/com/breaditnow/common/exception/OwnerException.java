package com.breaditnow.common.exception;

public class OwnerException extends BreaditnowException {

	public OwnerException(OwnerErrorCode errorCode) {
		super(errorCode);
	}

	public OwnerException(OwnerErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

