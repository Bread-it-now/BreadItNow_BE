package com.breaditnow.domain.domain.customer.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.global.exception.DomainException;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByOauth2Id(String oauth2Id);

	default Customer getById(Long id) {
		return findById(id).orElseThrow(() -> new DomainException(CUSTOMER_NOT_FOUND));
	}

	Optional<Customer> findByEmail(String email);
}
