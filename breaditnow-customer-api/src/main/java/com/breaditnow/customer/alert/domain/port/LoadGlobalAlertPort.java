package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.GlobalAlertSetting;

public interface LoadGlobalAlertPort {
    GlobalAlertSetting findByCustomerId(Long customerId);
}
