package com.breaditnow.domain.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.customer.entity.CustomerRegionPreference;

import java.util.List;

public interface CustomerRegionPreferenceRepository extends JpaRepository<CustomerRegionPreference, Long> {

    List<CustomerRegionPreference> findAllByCustomerId(Long customerId);

    void deleteAllByCustomerId(Long customerId);
}

