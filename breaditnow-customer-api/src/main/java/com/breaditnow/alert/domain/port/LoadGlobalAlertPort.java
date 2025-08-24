package com.breaditnow.alert.domain.port;

import com.breaditnow.alert.domain.GlobalAlertSetting;

public interface LoadGlobalAlertPort {
    GlobalAlertSetting findByCustomerId(Long customerId);
}
