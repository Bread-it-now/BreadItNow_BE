package com.breaditnow.customer.adapter.out.persistence.repository;

import com.breaditnow.customer.adapter.out.persistence.entity.CustomerRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRegionRepository extends JpaRepository<CustomerRegionEntity, Long> {
    void deleteByCustomerId(Long customerId);
}
