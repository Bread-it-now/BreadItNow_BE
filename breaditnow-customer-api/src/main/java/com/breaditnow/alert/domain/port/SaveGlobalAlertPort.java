package com.breaditnow.alert.domain.port;

import com.breaditnow.alert.domain.GlobalAlertSetting;

public interface SaveGlobalAlertPort {
    void save(Long customerId, GlobalAlertSetting globalAlertSetting);
}

