package com.breaditnow.customer.customer.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByNickname(String nickname);
    List<CustomerEntity> findAllByIdIn(List<Long> customerIds);
}
