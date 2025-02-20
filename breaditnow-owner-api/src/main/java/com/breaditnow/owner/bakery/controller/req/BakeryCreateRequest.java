package com.breaditnow.owner.bakery.controller.req;

public record BakeryCreateRequest(
	String name,
	String addressCode,
	String addressDescription,
	String phone,
	String openTime,
	String introduction,
	String profileImage
) {
}
