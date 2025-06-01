package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.DoNotDisturb;

public interface CustomerAlertSettingPort {
    DoNotDisturb findByCustomerId(Long customerId);
    void save(Long customerId, DoNotDisturb doNotDisturb);
}

