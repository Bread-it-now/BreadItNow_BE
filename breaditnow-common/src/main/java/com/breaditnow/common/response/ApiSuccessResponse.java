package com.breaditnow.common.response;

import static com.breaditnow.common.response.ResponseStatus.*;

import java.util.Map;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "data"})
public record ApiSuccessResponse<T>(
	ResponseStatus status,
	@Nullable T data) {

	public ApiSuccessResponse(T data) {
		this(SUCCESS, data);
	}

	public static <T> ApiSuccessResponse<T> of() {
		return new ApiSuccessResponse<>(null);
	}

	public static <T> ApiSuccessResponse<Map<String, T>> of(String key, T data) {
		return new ApiSuccessResponse<>(Map.of(key, data));
	}

	public static <T> ApiSuccessResponse<T> of(T data) {
		return new ApiSuccessResponse<>(data);
	}

}
