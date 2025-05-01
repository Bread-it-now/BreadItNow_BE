package com.breaditnow.owner.global.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class OwnerException extends BreaditnowException {

	public OwnerException(OwnerErrorCode errorCode) {
		super(errorCode);
	}

	public OwnerException(OwnerErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

