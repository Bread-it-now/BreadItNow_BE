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
	List<String> bakeryImages,
	boolean isActive
) {
	@Builder
	public BakeryResponse {
	}

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
			.isActive(bakery.isActive())
			.build();
	}
}
