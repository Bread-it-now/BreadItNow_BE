package com.breaditnow.owner.bakery.controller.req;

import java.util.List;

import com.breaditnow.domain.bakery.entity.BakeryImage;

public record BakeryUpdateRequest(
	String name,
	String addressCode,
	String addressDescription,
	String phone,
	String openTime,
	String introduction,
	String profileImage,
	List<BakeryImage> additionalImages
) {
}
