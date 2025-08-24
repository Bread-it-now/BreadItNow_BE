package com.breaditnow.alert.infrastructure;

import com.breaditnow.alert.domain.GlobalAlertSetting;
import com.breaditnow.alert.domain.port.LoadGlobalAlertPort;
import com.breaditnow.alert.domain.port.SaveGlobalAlertPort;
import com.breaditnow.alert.infrastructure.jpa.JpaGlobalAlertRepository;
import com.breaditnow.alert.infrastructure.jpa.entity.GlobalAlertSettingEntity;
import com.breaditnow.common.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.common.exception.CustomerErrorCode.ALERT_DND_SETTING_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class GlobalAlertAdapter implements LoadGlobalAlertPort, SaveGlobalAlertPort {
    private final JpaGlobalAlertRepository jpaGlobalAlertRepository;

    @Override
    public GlobalAlertSetting findByCustomerId(Long customerId) {
        return jpaGlobalAlertRepository.findByCustomerId(customerId)
                .map(GlobalAlertSettingEntity::toDomain)
                .orElseThrow(() -> new CustomerException(ALERT_DND_SETTING_NOT_FOUND));
    }

    @Override
    public void save(Long customerId, GlobalAlertSetting globalAlertSetting) {
        GlobalAlertSettingEntity entity = GlobalAlertSettingEntity.fromDomain(customerId, globalAlertSetting);
        jpaGlobalAlertRepository.save(entity);
    }
}
