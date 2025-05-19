package com.breaditnow.customer.common.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class CustomerException extends BreaditnowException {

	public CustomerException(CustomerErrorCode errorCode) {
		super(errorCode);
	}

	public CustomerException(CustomerErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

