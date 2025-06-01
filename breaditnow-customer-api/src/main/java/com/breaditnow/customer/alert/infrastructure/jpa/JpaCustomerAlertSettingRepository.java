package com.breaditnow.customer.alert.infrastructure.jpa;

import com.breaditnow.customer.alert.infrastructure.entity.CustomerAlertSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCustomerAlertSettingRepository extends JpaRepository<CustomerAlertSettingEntity, Long> {
    Optional<CustomerAlertSettingEntity> findByCustomerId(Long customerId);
}

