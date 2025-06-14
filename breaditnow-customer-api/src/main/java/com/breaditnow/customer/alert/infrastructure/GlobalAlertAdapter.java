package com.breaditnow.customer.alert.infrastructure;

import com.breaditnow.customer.alert.domain.GlobalAlertSetting;
import com.breaditnow.customer.alert.domain.port.LoadGlobalAlertPort;
import com.breaditnow.customer.alert.domain.port.SaveGlobalAlertPort;
import com.breaditnow.customer.alert.infrastructure.jpa.entity.GlobalAlertSettingEntity;
import com.breaditnow.customer.alert.infrastructure.jpa.JpaGlobalAlertRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_DND_SETTING_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class GlobalAlertAdapter implements LoadGlobalAlertPort, SaveGlobalAlertPort {
    private final JpaGlobalAlertRepository jpaGlobalAlertRepository;

    @Override
    public GlobalAlertSetting findByCustomerId(Long customerId) {
        return jpaGlobalAlertRepository.findByCustomerId(customerId)
                .map(GlobalAlertSettingEntity::toDomain)
                .orElseThrow(() -> new DomainException(ALERT_DND_SETTING_NOT_FOUND));
    }

    @Override
    public void save(Long customerId, GlobalAlertSetting globalAlertSetting) {
        GlobalAlertSettingEntity entity = GlobalAlertSettingEntity.fromDomain(customerId, globalAlertSetting);
        jpaGlobalAlertRepository.save(entity);
    }
}
