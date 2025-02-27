package com.breaditnow.owner.bakery.controller.res;

import java.util.List;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;

import lombok.Builder;

@Builder
public record BakeryResponse(
	Long bakeryId,
	String name,
	String addressDescription,
	String phone,
	String openTime,
	String introduction,
	String profileImage,
	List<String> bakeryImages,
	OperatingStatus operatingStatus
) {
	public static BakeryResponse of(Bakery bakery) {
		List<String> imageUrls = bakery.getBakeryImages()
			.stream()
			.map(BakeryImage::getImageUrl)
			.toList();

		return BakeryResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.phone(bakery.getPhone())
			.introduction(bakery.getIntroduction())
			.openTime(bakery.getOpenTime())
			.profileImage(bakery.getProfileImage())
			.addressDescription(bakery.getAddress().getDescription())
			.bakeryImages(imageUrls)
			.operatingStatus(bakery.getOperatingStatus())
			.build();
	}
}
