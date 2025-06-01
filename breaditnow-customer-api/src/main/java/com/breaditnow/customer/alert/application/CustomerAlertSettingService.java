package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.request.CustomerDoNotDisturbUpdateRequest;
import com.breaditnow.customer.alert.application.response.CustomerDoNotDisturbResponse;
import com.breaditnow.customer.alert.application.response.CustomerDoNotDisturbToggleResponse;
import com.breaditnow.customer.alert.domain.DayOfWeekSet;
import com.breaditnow.customer.alert.domain.DoNotDisturb;
import com.breaditnow.customer.alert.domain.ReleaseTime;
import com.breaditnow.customer.alert.domain.port.CustomerAlertSettingPort;
import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;
import com.breaditnow.domain.domain.alert.repository.CustomerAlertSettingRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerAlertSettingService {
    private final CustomerAlertSettingPort alertSettingPort;

    public CustomerDoNotDisturbResponse getDoNotDisturbSetting(Long customerId) {
        DoNotDisturb doNotDisturb = alertSettingPort.findByCustomerId(customerId);
        return CustomerDoNotDisturbResponse.of(doNotDisturb);
    }

    @Transactional
    public void updateDoNotDisturbSetting(Long customerId, CustomerDoNotDisturbUpdateRequest dto) {
        DoNotDisturb dnd = DoNotDisturb.of(dto.days(), dto.startTime(), dto.endTime(), true);
        alertSettingPort.save(customerId, dnd);
    }

    @Transactional
    public CustomerDoNotDisturbToggleResponse toggleSettings(Long customerId) {
        DoNotDisturb dnd = alertSettingPort.findByCustomerId(customerId);

        if (dnd.isActive()) {
            dnd.deactivate();
        } else {
            dnd.activate();
        }

        alertSettingPort.save(customerId, dnd);
        return new CustomerDoNotDisturbToggleResponse(dnd.isActive());
    }
}
