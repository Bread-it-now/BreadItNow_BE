package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.request.GlobalAlertSettingUpdateRequest;
import com.breaditnow.customer.alert.application.response.GlobalAlertSettingResponse;
import com.breaditnow.customer.alert.application.response.GlobalAlertSettingToggleResponse;
import com.breaditnow.customer.alert.domain.GlobalAlertSetting;
import com.breaditnow.customer.alert.domain.port.GlobalAlertSettingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GlobalAlertSettingService {
    private final GlobalAlertSettingPort globalAlertSettingPort;

    public GlobalAlertSettingResponse getDoNotDisturbSetting(Long customerId) {
        GlobalAlertSetting globalAlertSetting = globalAlertSettingPort.findByCustomerId(customerId);
        return GlobalAlertSettingResponse.of(globalAlertSetting);
    }

    @Transactional
    public void updateDoNotDisturbSetting(Long customerId, GlobalAlertSettingUpdateRequest dto) {
        GlobalAlertSetting dnd = GlobalAlertSetting.of(dto.days(), dto.startTime(), dto.endTime(), true);
        globalAlertSettingPort.save(customerId, dnd);
    }

    @Transactional
    public GlobalAlertSettingToggleResponse toggleSettings(Long customerId) {
        GlobalAlertSetting dnd = globalAlertSettingPort.findByCustomerId(customerId);

        if (dnd.isActive()) {
            dnd.deactivate();
        } else {
            dnd.activate();
        }

        globalAlertSettingPort.save(customerId, dnd);
        return new GlobalAlertSettingToggleResponse(dnd.isActive());
    }
}
