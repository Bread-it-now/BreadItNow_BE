package com.breaditnow.external.global.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class ExternalException extends BreaditnowException {

	public ExternalException(ExternalErrorCode errorCode) {
		super(errorCode);
	}

	public ExternalException(ExternalErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

