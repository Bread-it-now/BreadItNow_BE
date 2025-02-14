package com.breaditnow.common.response;

import static com.breaditnow.common.response.ResponseStatus.ERROR;

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
	@Nullable ErrorDetail errors)  {

	public ApiErrorResponse(String code, String message, ErrorDetail errors) {
		this(ERROR, code, message, errors);
	}

	public static ApiErrorResponse of(ErrorCode errorCode) {
		return new ApiErrorResponse(errorCode.defaultCode(), errorCode.defaultMessage(), null);
	}

	public static ApiErrorResponse of(String code, String message, ErrorDetail errors) {
		return new ApiErrorResponse(code, message, errors);
	}
}

