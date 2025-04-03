package com.breaditnow.customer.domain.alert.service;

import com.breaditnow.customer.domain.alert.controller.req.CustomerDoNotDisturbUpdateRequest;
import com.breaditnow.customer.domain.alert.controller.res.CustomerDoNotDisturbResponse;
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

    private final CustomerAlertSettingRepository alertSettingRepository;
    private final CustomerRepository customerRepository;

    public CustomerDoNotDisturbResponse getDoNotDisturbSetting(Long customerId) {
        CustomerAlertSetting setting = alertSettingRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new DomainException(ALERT_NOT_FOUND));

        return CustomerDoNotDisturbResponse.of(setting);
    }

    @Transactional
    public void updateDoNotDisturbSetting(Long customerId, CustomerDoNotDisturbUpdateRequest request) {
        Customer customer = customerRepository.getById(customerId);

        CustomerAlertSetting setting = alertSettingRepository.findByCustomerId(customerId)
                .orElseGet(() -> CustomerAlertSetting.builder()
                        .customer(customer)
                        .isActive(true)
                        .doNotDisturbDays(request.days())
                        .doNotDisturbStartTime(request.startTime())
                        .doNotDisturbEndTime(request.endTime())
                        .build()
                );

        if (setting.getId() != null) {
            setting.setDoNotDisturbDays(request.days());
            setting.setDoNotDisturbStartTime(request.startTime());
            setting.setDoNotDisturbEndTime(request.endTime());
            setting.setActive(true);
        }

        alertSettingRepository.save(setting);
    }
}
