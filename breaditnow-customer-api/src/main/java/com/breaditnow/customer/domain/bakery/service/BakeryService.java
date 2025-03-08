package com.breaditnow.customer.domain.bakery.service;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.customer.domain.product.controller.res.ProductResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryService {
	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;

	public BakeryDetailResponse getBakeryDetail(Long bakeryId) {
		Bakery bakery = bakeryRepository.getById(bakeryId);
		List<Product> products = productRepository.findByBakeryId(bakeryId);

		BakeryResponse bakeryResponse = BakeryResponse.builder()
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

		List<ProductResponse> productResponses = products.stream()
			.map(product -> ProductResponse.builder()
				.productId(product.getId())
				.bakeryId(bakery.getId())
				.productType(product.getType().name())
				.name(product.getName())
				.price(product.getPrice())
				.image(product.getImage())
				.description(product.getDescription())
				.breadCategoryIds(product.getBreadCategories()
					.stream()
					.map(o -> o.getBreadCategory().getId())
					.collect(toList())
				)
				.releaseTimes(Arrays.stream(product.getReleaseTime().split(";"))
					.map(String::trim)
					.collect(Collectors.toList())
				)
				.build()
			)
			.toList();

		return BakeryDetailResponse.builder()
			.store(bakeryResponse)
			.products(productResponses)
			.build();
	}
}
