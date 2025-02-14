package com.breaditnow.common.response;

import static com.breaditnow.common.response.ResponseStatus.*;

import java.util.List;

import org.springframework.lang.Nullable;

import com.breaditnow.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "code", "message", "errors"})
public record ApiErrorResponse(
	ResponseStatus status,
	String code,
	String message,
	@Nullable List<ErrorDetail> errors)  {

	public ApiErrorResponse(String code, String message, List<ErrorDetail> errors) {
		this(ERROR, code, message, errors);
	}

	public static ApiErrorResponse of(ErrorCode errorCode) {
		return new ApiErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
	}

	public static ApiErrorResponse of(ErrorCode errorCode, List<ErrorDetail> errors) {
		return new ApiErrorResponse(errorCode.getCode(), errorCode.getMessage(), errors);
	}

	public static ApiErrorResponse of(ErrorCode errorCode, String exMessage) {
		return new ApiErrorResponse(errorCode.getCode(), errorCode.getMessage() + exMessage, null);
	}
}

