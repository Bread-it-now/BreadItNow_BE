package com.breaditnow.auth.domain.customer.service;

import static com.breaditnow.domain.domain.customer.enumerate.Provider.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.auth.domain.customer.controller.req.SignupRequest;
import com.breaditnow.common.security.Role;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
	private final CustomerRepository customerRepository;
	private final OwnerRepository ownerRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long signup(SignupRequest signupRequest) {
		Role role = Role.from(signupRequest.role());

		if (role == Role.CUSTOMER) {
			Customer customer = Customer.builder()
				.email(signupRequest.email())
				.password(passwordEncoder.encode(signupRequest.password()))
				.provider(EMAIL)
				.build();
			Customer savedCustomer = customerRepository.save(customer);
			return savedCustomer.getId();
		} else {
			Owner owner = Owner.builder()
				.email(signupRequest.email())
				.password(passwordEncoder.encode(signupRequest.password()))
				.build();
			Owner savedOwner = ownerRepository.save(owner);
			return savedOwner.getId();
		}
	}
}
