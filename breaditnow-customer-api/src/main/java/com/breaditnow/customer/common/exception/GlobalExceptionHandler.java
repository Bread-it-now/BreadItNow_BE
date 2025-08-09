package com.breaditnow.customer.common.exception;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.exception.ErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.breaditnow.common.response.ErrorDetail;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.breaditnow.common.exception.CommonErrorCode.*;
import static java.util.stream.Collectors.toList;

@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ApiErrorResponse handleMethodArgumentNotValidException(
		MethodArgumentNotValidException ex) {
		log.error("[{}] {}", ex.getClass().getName(), ex.getMessage());
		List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
			.collect(toList());
		return ApiErrorResponse.of(INVALID_PARAMETER, errors);
	}

	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		log.error("[{}] {}", ex.getClass().getName(), ex.getMessage());
		Throwable root = NestedExceptionUtils.getRootCause(ex);

		if (root instanceof BreaditnowException customerEx) {
			ErrorCode errorCode = customerEx.getErrorCode();
			return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ApiErrorResponse.of(errorCode));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiErrorResponse.of(ARGUMENT_TYPE_MISMATCH, ex.getMessage()));
	}

	@ExceptionHandler(value = BreaditnowException.class)
	public ResponseEntity<ApiErrorResponse> handleBusinessException(BreaditnowException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		log.error("[{}] code={}, message={}",
			ex.getClass().getName(),
			errorCode.getCode(),
			ex.getMessage()
		);
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(ApiErrorResponse.of(errorCode));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
		log.error("[{}] {}", ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiErrorResponse.of(UNDEFINED_ERROR, ex.getMessage()));
	}
}
