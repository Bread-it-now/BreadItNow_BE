package com.breaditnow.domain.domain.customer.repository;

import com.breaditnow.domain.domain.customer.entity.CustomerRegionPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRegionPreferenceRepository extends JpaRepository<CustomerRegionPreference, Long> {

    void deleteAllByCustomerId(Long customerId);
}

