package com.breaditnow.customer.domain.customer.infrastructure.jpa;

import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerRegionEntity;
import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerRegionIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRegionRepository extends JpaRepository<CustomerRegionEntity, CustomerRegionIdEntity> {
    void deleteAllById_CustomerId(Long customerId);
}
