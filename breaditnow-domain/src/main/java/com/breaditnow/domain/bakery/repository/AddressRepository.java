package com.breaditnow.domain.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.bakery.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
