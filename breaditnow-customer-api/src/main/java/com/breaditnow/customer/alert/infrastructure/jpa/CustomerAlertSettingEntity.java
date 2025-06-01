package com.breaditnow.customer.alert.infrastructure.jpa;

import com.breaditnow.customer.alert.domain.DayOfWeekSet;
import com.breaditnow.customer.alert.domain.DoNotDisturb;
import com.breaditnow.customer.alert.domain.ReleaseTime;
import com.breaditnow.customer.common.exception.CustomerErrorCode;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.common.infrastructure.converter.DayOfWeekSetConverter;
import com.breaditnow.customer.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Entity
@Table(name = "customer_alert_setting")
@NoArgsConstructor
@Getter
public class CustomerAlertSettingEntity {
    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "dnd_active", nullable = false)
    private boolean active;

    @Convert(converter = DayOfWeekSetConverter.class)
    @Column(name = "dnd_days", nullable = false)
    private DayOfWeekSet days;

    @Column(name = "dnd_start_time", columnDefinition = "TIME", nullable = false)
    private LocalTime startTime;

    @Column(name = "dnd_end_time", columnDefinition = "TIME", nullable = false)
    private LocalTime endTime;

    public static CustomerAlertSettingEntity fromDomain(Long customerId, DoNotDisturb dnd) {
        CustomerAlertSettingEntity entity = new CustomerAlertSettingEntity();
        entity.customerId = customerId;
        entity.active = dnd.isActive();
        entity.days = dnd.getDays();
        entity.startTime = dnd.getStartTime().toLocalTime();
        entity.endTime = dnd.getEndTime().toLocalTime();
        return entity;
    }

    public DoNotDisturb toDomain() {
        return DoNotDisturb.builder()
                .days(days)
                .startTime(ReleaseTime.fromLocalTime(startTime))
                .endTime(ReleaseTime.fromLocalTime(endTime))
                .active(active)
                .build();
    }
}
