package com.breaditnow.owner.bakery.controller.res;

import java.util.List;

import com.breaditnow.domain.bakery.entity.Bakery;
import com.breaditnow.domain.bakery.entity.BakeryImage;

import lombok.Builder;

public record BakeryResponse(
	Long bakeryId,
	String name,
	String phone,
	String addressDescription,
	String openTime,
	String introduction,
	String profileImage,
	List<BakeryImage> additionalImage
) {
	@Builder
	public BakeryResponse {
	}

	public static BakeryResponse of(Bakery bakery) {
		return BakeryResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.addressDescription(bakery.getAddress().getDescription())
			.phone(bakery.getPhone())
			.openTime(bakery.getOpenTime())
			.introduction(bakery.getIntroduction())
			.profileImage(bakery.getProfileImage())
			.additionalImage(bakery.getBakeryImages())
			.build();
	}
}
