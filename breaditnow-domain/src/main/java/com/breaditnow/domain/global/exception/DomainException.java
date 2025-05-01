package com.breaditnow.domain.global.exception;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.exception.ErrorCode;

public class DomainException extends BreaditnowException {

	public DomainException(ErrorCode errorCode) {
		super(errorCode);
	}

	public DomainException(ErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
