package com.breaditnow.customer.domain.bakery.controller.res;

import static java.util.stream.Collectors.*;

import java.util.List;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;

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
	List<String> additionalImages,
	OperatingStatus operatingStatus
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
			.additionalImages(bakery.getAdditionalImages()
				.stream()
				.map(BakeryImage::getImageUrl)
				.collect(toList()))
			.operatingStatus(bakery.getOperatingStatus())
			.build();
	}
}
