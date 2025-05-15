package com.breaditnow.customer.domain.customer.infrastructure.jpa;

import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByNickname(String nickname);
}
