package com.breaditnow.common.exception;

import static com.breaditnow.common.exception.CommonErrorCode.*;
import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.breaditnow.common.response.ApiErrorResponse;
import com.breaditnow.common.response.ErrorDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ApiErrorResponse handleMethodArgumentNotValidException(
		MethodArgumentNotValidException ex) {
		List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
			.collect(toList());
		return ApiErrorResponse.of(INVALID_PARAMETER, errors);
	}

	@ExceptionHandler(value = BreaditnowException.class)
	public ResponseEntity<ApiErrorResponse> handleBusinessException(BreaditnowException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(ApiErrorResponse.of(errorCode));
	}

	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		// (A) 스프링 유틸 메서드 사용
		Throwable root = NestedExceptionUtils.getRootCause(ex);

		if (root instanceof BreaditnowException) {
			// 여기서 내가 원하는 처리
			BreaditnowException customerEx = (BreaditnowException)root;
			ErrorCode errorCode = customerEx.getErrorCode();
			return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ApiErrorResponse.of(errorCode));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiErrorResponse.of(UNDEFINED_ERROR, ex.getMessage()));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiErrorResponse.of(UNDEFINED_ERROR, ex.getMessage()));
	}
}
