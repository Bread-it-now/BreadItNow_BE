package com.breaditnow.common.page;

public record PageInfoRequest(
	int page,
	int size,
	String sort) {
}
