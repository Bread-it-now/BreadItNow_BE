package com.breaditnow.common.exception;

public class CustomerException extends BreaditnowException {

	public CustomerException(CustomerErrorCode errorCode) {
		super(errorCode);
	}

	public CustomerException(CustomerErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

