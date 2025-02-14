package com.breaditnow.domain.bakery.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.breaditnow.domain.bakery.enumerate.OperatingStatus;

class BakeryTest {
	@Test
	void create_bakery() {
		Address address = Address.createAddressBuilder()
			.city("서울특별시")
			.region("강남구")
			.zipcode("12345")
			.latitude(37.1234)
			.longitude(127.5678)
			.description("강남대로 123")
			.build();

		Bakery bakery = Bakery.createBakeryBuilder()
			.name("빵집 이름")
			.phone("010-1234-5678")
			.introduction("맛있는 빵집입니다.")
			.profileImage("http://example.com/profile.jpg")
			.openTime("09:00 - 21:00")
			.address(address)
			.operatingStatus(OperatingStatus.OPEN)
			.build();

		System.out.println(bakery);
		assertThat(bakery).isNotNull();
		assertThat(bakery.getName()).isEqualTo("빵집 이름");
		assertThat(bakery.getAddress().getCity()).isEqualTo("서울특별시");
	}
}
