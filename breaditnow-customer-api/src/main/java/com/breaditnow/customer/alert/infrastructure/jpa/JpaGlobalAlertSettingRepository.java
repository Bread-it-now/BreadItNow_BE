package com.breaditnow.customer.alert.infrastructure.jpa;

import com.breaditnow.customer.alert.infrastructure.entity.GlobalAlertSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaGlobalAlertSettingRepository extends JpaRepository<GlobalAlertSettingEntity, Long> {
    Optional<GlobalAlertSettingEntity> findByCustomerId(Long customerId);
}

