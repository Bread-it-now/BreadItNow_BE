package com.breaditnow.customer.domain.bakery.controller.res;

import static com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse.*;
import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;

class BreadReleaseScheduleResponseTest {
	private Product createProduct(ProductType type, String name, String releaseTime) {
		return Product.builder()
			.type(type)
			.name(name)
			.releaseTime(releaseTime)
			.build();
	}

	@Test
	@DisplayName("빈 제품 리스트인 경우, releaseSchedules는 빈 리스트여야 한다")
	public void testEmptyProducts() {
		// when
		List<BreadReleaseScheduleResponse> schedules = groupReleaseSchedules(List.of());

		// then
		assertThat(schedules).isEmpty();
	}

	@Test
	@DisplayName("BREAD 타입 제품이 없으면 releaseSchedules는 빈 리스트여야 한다")
	public void testNoBreadProducts() {
		// given
		Product p1 = createProduct(OTHER, "아메리카노", "09:00;10:00");

		// when
		List<BreadReleaseScheduleResponse> schedules = groupReleaseSchedules(List.of(p1));

		// then
		assertThat(schedules).isEmpty();
	}

	@Test
	@DisplayName("BREAD 타입 제품의 releaseTime이 null이면 releaseSchedules에 포함되지 않는다")
	public void testBreadProductNullReleaseTime() {
		// given
		Product p1 = createProduct(BREAD, "식빵", null);

		// when
		List<BreadReleaseScheduleResponse> schedules = groupReleaseSchedules(List.of(p1));

		// then
		assertThat(schedules).isEmpty();
	}

	@Test
	@DisplayName("BREAD 타입 제품의 releaseTime이 빈 문자열이면 releaseSchedules에 포함되지 않는다")
	public void testBreadProductEmptyReleaseTime() {
		// given
		Product p1 = createProduct(BREAD, "식빵", "   ");

		// when
		List<BreadReleaseScheduleResponse> schedules = groupReleaseSchedules(List.of(p1));

		// then
		assertThat(schedules).isEmpty();
	}

	@Test
	@DisplayName("단일 BREAD 제품의 여러 releaseTime을 올바르게 분리하고 그룹화해야 한다")
	public void testSingleProductMultipleReleaseTimes() {
		// given
		Product p1 = createProduct(BREAD, "식빵", "08:30;12:00;16:00");

		// when
		List<BreadReleaseScheduleResponse> schedules = groupReleaseSchedules(List.of(p1));

		// then
		assertThat(schedules).hasSize(3);

		assertThat(schedules.get(0).releaseTime()).isEqualTo("08:30");
		assertThat(schedules.get(1).releaseTime()).isEqualTo("12:00");
		assertThat(schedules.get(2).releaseTime()).isEqualTo("16:00");
	}

	@Test
	@DisplayName("여러 BREAD 제품의 releaseTime이 중복될 경우 올바르게 그룹화해야 한다")
	public void testMultipleProductsOverlappingReleaseTimes() {
		// given
		Product p1 = createProduct(BREAD, "식빵", "08:30;12:00;16:00");
		Product p2 = createProduct(BREAD, "크루아상", "08:30;14:00");

		// when
		List<BreadReleaseScheduleResponse> schedules = groupReleaseSchedules(List.of(p1, p2));

		// then
		// 예상 그룹: "08:30" (p1, p2), "12:00" (p1), "14:00" (p2), "16:00" (p1)
		assertThat(schedules).hasSize(4);
		Map<String, BreadReleaseScheduleResponse> scheduleMap = schedules.stream()
			.collect(Collectors.toMap(BreadReleaseScheduleResponse::releaseTime, s -> s));

		assertThat(scheduleMap.get("08:30").scheduledProducts()).hasSize(2);
		assertThat(scheduleMap.get("12:00").scheduledProducts()).hasSize(1);
		assertThat(scheduleMap.get("14:00").scheduledProducts()).hasSize(1);
		assertThat(scheduleMap.get("16:00").scheduledProducts()).hasSize(1);
	}
}
