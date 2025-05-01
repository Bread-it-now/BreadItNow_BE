package com.breaditnow.common.response;

public record ErrorDetail(
	String field,
	String message) {
}
