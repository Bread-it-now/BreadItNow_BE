package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.GlobalAlertSetting;

public interface SaveGlobalAlertPort {
    void save(Long customerId, GlobalAlertSetting globalAlertSetting);
}

