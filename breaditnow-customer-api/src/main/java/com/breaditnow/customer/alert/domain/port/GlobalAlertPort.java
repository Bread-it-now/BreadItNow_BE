package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.GlobalAlertSetting;

public interface GlobalAlertPort {
    GlobalAlertSetting findByCustomerId(Long customerId);
    void save(Long customerId, GlobalAlertSetting globalAlertSetting);
}

