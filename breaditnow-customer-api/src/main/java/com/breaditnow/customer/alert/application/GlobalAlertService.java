package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertToggleResponse;
import com.breaditnow.customer.alert.domain.GlobalAlertSetting;
import com.breaditnow.customer.alert.domain.port.GlobalAlertPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GlobalAlertService {
    private final GlobalAlertPort globalAlertPort;

    public GlobalAlertResponse getDoNotDisturbSetting(Long customerId) {
        GlobalAlertSetting globalAlertSetting = globalAlertPort.findByCustomerId(customerId);
        return GlobalAlertResponse.of(globalAlertSetting);
    }

    @Transactional
    public void updateDoNotDisturbSetting(Long customerId, GlobalAlertUpdateRequest dto) {
        GlobalAlertSetting dnd = GlobalAlertSetting.of(dto.days(), dto.startTime(), dto.endTime(), true);
        globalAlertPort.save(customerId, dnd);
    }

    @Transactional
    public GlobalAlertToggleResponse toggleSettings(Long customerId) {
        GlobalAlertSetting globalAlertSetting = globalAlertPort.findByCustomerId(customerId);
        globalAlertSetting.toggle();
        globalAlertPort.save(customerId, globalAlertSetting);
        return new GlobalAlertToggleResponse(globalAlertSetting.isActive());
    }
}
