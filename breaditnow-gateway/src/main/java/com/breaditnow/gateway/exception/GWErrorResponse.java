package com.breaditnow.gateway.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "code", "message", "errors"})
public record GWErrorResponse(
	String status,
	String code,
	String message) {

	public static GWErrorResponse of(GatewayErrorCode errorCode) {
		return new GWErrorResponse("ERROR", errorCode.getCode(), errorCode.getMessage());
	}

	public static GWErrorResponse of(GatewayErrorCode errorCode, String exMessage) {
		return new GWErrorResponse("ERROR", errorCode.getCode(), errorCode.getMessage() + " " + exMessage);
	}
}
