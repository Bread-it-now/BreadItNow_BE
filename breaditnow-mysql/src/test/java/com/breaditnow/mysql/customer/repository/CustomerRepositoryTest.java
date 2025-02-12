package com.breaditnow.mysql.customer.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.breaditnow.mysql.customer.entity.Customer;
import com.breaditnow.mysql.customer.enumerate.Provider;

@SpringBootTest
class CustomerRepositoryTest {
	@Autowired
	private CustomerRepository customerRepository;

	Customer customer;

	@BeforeEach
	void init() {
		customer = Customer.createCustomerBuilder()
			.email("john.doe@example.com")
			.password("password123")
			.nickname("John Doe")
			.phone("123-456-7890")
			.provider(Provider.EMAIL)
			.profileImage("image_url")
			.build();
	}

	@Test
	void 고객을_저장한다() {
		Customer savedCustomer = customerRepository.save(customer);

		assertThat(savedCustomer.getId()).isNotNull();

		// 고객이 정상적으로 저장되었는지 확인한다.
		assertThat(savedCustomer.getEmail()).isEqualTo(customer.getEmail());
	}
}
