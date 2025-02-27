package com.breaditnow.gateway.exception;

public class GatewayException extends RuntimeException {

	private final GatewayErrorCode errorCode;

	public GatewayException(GatewayErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public GatewayException(GatewayErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}

	public GatewayException(GatewayErrorCode errorCode, String message) {
		super(errorCode.getMessage() + " : " + message);
		this.errorCode = errorCode;
	}

}
