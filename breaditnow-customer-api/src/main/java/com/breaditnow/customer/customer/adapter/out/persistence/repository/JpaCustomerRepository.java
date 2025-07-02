package com.breaditnow.customer.customer.adapter.out.persistence.repository;

import com.breaditnow.customer.customer.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByNickname(String nickname);
    List<CustomerEntity> findAllByIdIn(List<Long> customerIds);
}
