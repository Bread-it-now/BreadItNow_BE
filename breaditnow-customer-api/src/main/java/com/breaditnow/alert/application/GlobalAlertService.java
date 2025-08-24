package com.breaditnow.alert.application;

import com.breaditnow.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.alert.application.response.GlobalAlertResponse;
import com.breaditnow.alert.application.response.GlobalAlertToggleResponse;
import com.breaditnow.alert.domain.GlobalAlertSetting;
import com.breaditnow.alert.domain.port.LoadGlobalAlertPort;
import com.breaditnow.alert.domain.port.SaveGlobalAlertPort;
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
