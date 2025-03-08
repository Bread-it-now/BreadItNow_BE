package com.breaditnow.customer.domain.bakery.controller.res;

import static java.util.stream.Collectors.*;

import java.util.List;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;

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

	public static BakeryResponse of(Bakery bakery) {
		return BakeryResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.address(bakery.getAddress().getDescription())
			.phone(bakery.getPhone())
			.openTime(bakery.getOpenTime())
			.introduction(bakery.getIntroduction())
			.profileImage(bakery.getProfileImage())
			.additionalImage(bakery.getBakeryImages()
				.stream()
				.map(BakeryImage::getImageUrl)
				.collect(toList()))
			.build();
	}
}
