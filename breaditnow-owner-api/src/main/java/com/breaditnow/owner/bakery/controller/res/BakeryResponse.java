package com.breaditnow.owner.bakery.controller.res;

import java.util.List;

import lombok.Builder;

public record BakeryResponse(
	Long bakeryId,
	String name,
	String phone,
	String addressDescription,
	String openTime,
	String introduction,
	String profileImage,
	List<String> additionalImage
) {
	@Builder
	public BakeryResponse {
	}
}
