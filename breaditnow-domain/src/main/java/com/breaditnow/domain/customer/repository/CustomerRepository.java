package com.breaditnow.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
