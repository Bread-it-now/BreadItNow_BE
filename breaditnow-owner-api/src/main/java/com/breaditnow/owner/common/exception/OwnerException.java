package com.breaditnow.owner.common.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class OwnerException extends BreaditnowException {

	public OwnerException(OwnerErrorCode errorCode) {
		super(errorCode);
	}

	public OwnerException(OwnerErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

