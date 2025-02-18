package com.breaditnow.auth.global.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class AuthenticationException extends BreaditnowException {

	public AuthenticationException(AuthenticationErrorCode errorCode) {
		super(errorCode);
	}

	public AuthenticationException(AuthenticationErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

