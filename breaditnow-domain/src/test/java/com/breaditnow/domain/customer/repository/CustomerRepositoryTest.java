package com.breaditnow.domain.customer.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.breaditnow.domain.customer.entity.Customer;
import com.breaditnow.domain.customer.enumerate.Provider;

@SpringBootTest
class CustomerRepositoryTest {
	@Autowired
	private CustomerRepository customerRepository;

	Customer customer;

	@BeforeEach
	void init() {
		customer = Customer.builder()
			.email("john.doe@example.com")
			.password("password123")
			.nickname("John Doe")
			.phone("123-456-7890")
			.provider(Provider.EMAIL)
			.profileImage("image_url")
			.build();
	}

	@Test
	void save_customer() {
		Customer savedCustomer = customerRepository.save(customer);

		assertThat(savedCustomer.getId()).isNotNull();

		assertThat(savedCustomer.getEmail()).isEqualTo(customer.getEmail());
	}
}
