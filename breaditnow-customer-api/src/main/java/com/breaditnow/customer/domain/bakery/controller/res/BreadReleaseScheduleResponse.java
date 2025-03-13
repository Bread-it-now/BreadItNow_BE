package com.breaditnow.customer.domain.bakery.controller.res;

import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record BreadReleaseScheduleResponse(
	String releaseTime,
	List<SimpleProductResponse> scheduledProducts
) {
	public static List<BreadReleaseScheduleResponse> groupReleaseSchedules(List<Product> products) {
		Map<String, List<SimpleProductResponse>> scheduleMap = products.stream()
			.filter(product -> BREAD == product.getType())
			.flatMap(BreadReleaseScheduleResponse::extractReleaseSchedule)
			.collect(Collectors.groupingBy(
				Map.Entry::getKey,
				Collectors.mapping(Map.Entry::getValue, Collectors.toList())
			));

		return scheduleMap.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(entry -> new BreadReleaseScheduleResponse(entry.getKey(), entry.getValue()))
			.toList();
	}

	public static Stream<Map.Entry<String, SimpleProductResponse>> extractReleaseSchedule(Product product) {
		return Optional.ofNullable(product.getReleaseTime())
			.map(times -> Arrays.stream(times.split(";"))
				.map(String::trim)
				.filter(time -> !time.isEmpty()))
			.orElseGet(Stream::empty)
			.map(time -> new AbstractMap.SimpleEntry<>(time,
				new SimpleProductResponse(product.getId(), product.getName())));
	}
}
