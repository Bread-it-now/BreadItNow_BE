package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertToggleResponse;
import com.breaditnow.customer.alert.domain.GlobalAlertSetting;
import com.breaditnow.customer.alert.domain.port.LoadGlobalAlertPort;
import com.breaditnow.customer.alert.domain.port.SaveGlobalAlertPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GlobalAlertService {
    private final SaveGlobalAlertPort saveGlobalAlertPort;
    private final LoadGlobalAlertPort loadGlobalAlertPort;

    public GlobalAlertResponse getDoNotDisturbSetting(Long customerId) {
        GlobalAlertSetting globalAlertSetting = loadGlobalAlertPort.findByCustomerId(customerId);
        return GlobalAlertResponse.of(globalAlertSetting);
    }

    @Transactional
    public void updateDoNotDisturbSetting(Long customerId, GlobalAlertUpdateRequest request) {
        GlobalAlertSetting dnd = GlobalAlertSetting.create(request.days(), request.startTime(), request.endTime(), true);
        saveGlobalAlertPort.save(customerId, dnd);
    }

    @Transactional
    public GlobalAlertToggleResponse toggleSettings(Long customerId) {
        GlobalAlertSetting globalAlertSetting = loadGlobalAlertPort.findByCustomerId(customerId);
        globalAlertSetting.toggle();
        saveGlobalAlertPort.save(customerId, globalAlertSetting);
        return new GlobalAlertToggleResponse(globalAlertSetting.isActive());
    }
}
