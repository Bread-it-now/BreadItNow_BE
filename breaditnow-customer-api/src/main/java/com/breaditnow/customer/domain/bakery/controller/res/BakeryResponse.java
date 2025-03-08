package com.breaditnow.customer.domain.bakery.controller.res;

import java.util.List;

import lombok.Builder;

@Builder
public record BakeryResponse(
	Long bakeryId,
	String name,
	String address,
	String phone,
	String openTime,
	String introduction,
	String profileImage,
	List<String> additionalImage
) {
}
