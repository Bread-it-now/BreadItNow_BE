package com.breaditnow.domain.customer.entity;

import org.junit.jupiter.api.Test;

class CustomerTest {
	@Test
	void builder를_테스트한다() {
		Customer customer = Customer.builder()
			.email("email@naver.com")
			.password("password1!")
			.build();

		System.out.println(customer);
	}
}
