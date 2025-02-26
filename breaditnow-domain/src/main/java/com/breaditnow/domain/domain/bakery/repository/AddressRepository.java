package com.breaditnow.domain.domain.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.bakery.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
