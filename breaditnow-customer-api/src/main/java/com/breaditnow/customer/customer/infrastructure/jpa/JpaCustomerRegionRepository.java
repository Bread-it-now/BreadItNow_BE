package com.breaditnow.customer.customer.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRegionRepository extends JpaRepository<CustomerRegionEntity, CustomerRegionIdEntity> {
    void deleteAllById_CustomerId(Long customerId);
}
