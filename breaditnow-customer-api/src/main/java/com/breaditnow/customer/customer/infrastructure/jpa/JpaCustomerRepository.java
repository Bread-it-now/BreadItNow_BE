package com.breaditnow.customer.customer.infrastructure.jpa;

import com.breaditnow.customer.customer.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByNickname(String nickname);
}
