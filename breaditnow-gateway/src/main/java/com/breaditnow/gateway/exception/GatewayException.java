package com.breaditnow.gateway.exception;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.exception.CommonErrorCode;

public class GatewayException extends BreaditnowException {

	public GatewayException(GatewayErrorCode errorCode) {
		super(errorCode);
	}

	public GatewayException(GatewayErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public GatewayException(GatewayErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public GatewayException(CommonErrorCode commonErrorCode) {
		super(commonErrorCode);
	}
}
