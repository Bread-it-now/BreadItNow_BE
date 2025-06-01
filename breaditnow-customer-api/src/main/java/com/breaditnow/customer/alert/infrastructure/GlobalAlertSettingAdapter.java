package com.breaditnow.customer.alert.infrastructure;

import com.breaditnow.customer.alert.domain.GlobalAlertSetting;
import com.breaditnow.customer.alert.domain.port.GlobalAlertSettingPort;
import com.breaditnow.customer.alert.infrastructure.entity.GlobalAlertSettingEntity;
import com.breaditnow.customer.alert.infrastructure.jpa.JpaGlobalAlertSettingRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_DND_SETTING_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class GlobalAlertSettingAdapter implements GlobalAlertSettingPort {
    private final JpaGlobalAlertSettingRepository jpaGlobalAlertSettingRepository;

    @Override
    public GlobalAlertSetting findByCustomerId(Long customerId) {
        return jpaGlobalAlertSettingRepository.findByCustomerId(customerId)
                .map(GlobalAlertSettingEntity::toDomain)
                .orElseThrow(() -> new DomainException(ALERT_DND_SETTING_NOT_FOUND));
    }

    @Override
    public void save(Long customerId, GlobalAlertSetting globalAlertSetting) {
        GlobalAlertSettingEntity entity = GlobalAlertSettingEntity.fromDomain(customerId, globalAlertSetting);
        jpaGlobalAlertSettingRepository.save(entity);
    }
}
