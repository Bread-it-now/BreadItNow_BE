package com.breaditnow.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.customer.entity.CustomerRegionPreference;

public interface CustomerRegionPreferenceRepository extends JpaRepository<CustomerRegionPreference, Long> {

}

