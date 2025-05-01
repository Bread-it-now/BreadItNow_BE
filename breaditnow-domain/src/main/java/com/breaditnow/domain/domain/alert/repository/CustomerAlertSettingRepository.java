package com.breaditnow.domain.domain.alert.repository;

import com.breaditnow.domain.global.exception.DomainException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;

import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_NOT_FOUND;

public interface CustomerAlertSettingRepository extends JpaRepository<CustomerAlertSetting, Long> {

    default CustomerAlertSetting getByCustomerId(Long customerId) {
        return findByCustomerId(customerId)
                .orElseThrow(() -> new DomainException(ALERT_NOT_FOUND));
    }

    Optional<CustomerAlertSetting> findByCustomerId(Long customerId);
}

