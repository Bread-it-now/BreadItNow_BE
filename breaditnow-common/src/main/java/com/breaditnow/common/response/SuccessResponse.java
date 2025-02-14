package com.breaditnow.common.response;

import static com.breaditnow.common.response.ResponseStatus.SUCCESS;

import java.util.Map;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "data"})
public record SuccessResponse<T>(
	ResponseStatus status,
	@Nullable T data)  {

	public SuccessResponse(T data) {
		this(SUCCESS, data);
	}

	public static <T> SuccessResponse<T> of() {
		return new SuccessResponse<>(null);
	}

	public static <T> SuccessResponse<Map<String, T>> of(String key, T data) {
		return new SuccessResponse<>(Map.of(key, data));
	}

	public static <T> SuccessResponse<T> of(T data) {
		return new SuccessResponse<>(data);
	}

}
