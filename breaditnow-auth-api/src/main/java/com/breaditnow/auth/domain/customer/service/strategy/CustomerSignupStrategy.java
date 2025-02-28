package com.breaditnow.auth.domain.customer.service.strategy;

import static com.breaditnow.domain.domain.customer.enumerate.Provider.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.breaditnow.auth.domain.customer.controller.req.SignupRequest;
import com.breaditnow.auth.global.security.Role;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerSignupStrategy implements SignupStrategy {
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Long signup(SignupRequest signupRequest) {
		Customer customer = Customer.builder()
			.email(signupRequest.email())
			.password(passwordEncoder.encode(signupRequest.password()))
			.provider(EMAIL)
			.build();
		return customerRepository.save(customer).getId();
	}

	@Override
	public Role getRole() {
		return Role.CUSTOMER;
	}
}
