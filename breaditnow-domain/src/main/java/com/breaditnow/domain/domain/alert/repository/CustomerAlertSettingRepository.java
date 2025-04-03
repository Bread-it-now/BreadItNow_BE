package com.breaditnow.domain.domain.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;

import java.util.Optional;

public interface CustomerAlertSettingRepository extends JpaRepository<CustomerAlertSetting, Long> {

    Optional<CustomerAlertSetting> findByCustomerId(Long customerId);
}

