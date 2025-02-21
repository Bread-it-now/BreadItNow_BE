package com.breaditnow.domain.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.customer.entity.Customer;

import jakarta.persistence.EntityNotFoundException;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByOauth2Id(String oauth2Id);

	default Customer getById(Long id) {
		return findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " not found"));
	}
}
