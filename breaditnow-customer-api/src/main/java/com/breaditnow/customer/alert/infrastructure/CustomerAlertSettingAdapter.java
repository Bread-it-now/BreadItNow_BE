package com.breaditnow.customer.alert.infrastructure;

import com.breaditnow.customer.alert.domain.DoNotDisturb;
import com.breaditnow.customer.alert.domain.port.CustomerAlertSettingPort;
import com.breaditnow.customer.alert.infrastructure.entity.CustomerAlertSettingEntity;
import com.breaditnow.customer.alert.infrastructure.jpa.JpaCustomerAlertSettingRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_DND_SETTING_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class CustomerAlertSettingAdapter implements CustomerAlertSettingPort {
    private final JpaCustomerAlertSettingRepository jpaCustomerAlertSettingRepository;

    @Override
    public DoNotDisturb findByCustomerId(Long customerId) {
        return jpaCustomerAlertSettingRepository.findByCustomerId(customerId)
                .map(CustomerAlertSettingEntity::toDomain)
                .orElseThrow(() -> new DomainException(ALERT_DND_SETTING_NOT_FOUND));
    }

    @Override
    public void save(Long customerId, DoNotDisturb doNotDisturb) {
        CustomerAlertSettingEntity entity = CustomerAlertSettingEntity.fromDomain(customerId, doNotDisturb);
        jpaCustomerAlertSettingRepository.save(entity);
    }
}
