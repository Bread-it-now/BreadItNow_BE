package com.breaditnow.owner.domain.bakery.controller.req;

public record BakeryUpdateRequest(
	String name,
	String addressCode,
	String addressDescription,
	String phone,
	String openTime,
	String introduction
) {
}
