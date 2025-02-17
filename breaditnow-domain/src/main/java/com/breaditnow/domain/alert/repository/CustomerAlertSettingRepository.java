package com.breaditnow.domain.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.alert.entity.CustomerAlertSetting;

public interface CustomerAlertSettingRepository extends JpaRepository<CustomerAlertSetting, Long> {

}

