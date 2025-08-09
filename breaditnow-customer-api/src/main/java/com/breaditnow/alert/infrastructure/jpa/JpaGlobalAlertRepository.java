package com.breaditnow.alert.infrastructure.jpa;

import com.breaditnow.alert.infrastructure.jpa.entity.GlobalAlertSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaGlobalAlertRepository extends JpaRepository<GlobalAlertSettingEntity, Long> {
    Optional<GlobalAlertSettingEntity> findByCustomerId(Long customerId);
}

