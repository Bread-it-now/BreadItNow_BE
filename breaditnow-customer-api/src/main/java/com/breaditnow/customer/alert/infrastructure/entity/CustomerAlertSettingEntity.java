package com.breaditnow.customer.alert.infrastructure.entity;

import com.breaditnow.customer.alert.domain.DayOfWeekSet;
import com.breaditnow.customer.alert.domain.DoNotDisturb;
import com.breaditnow.customer.alert.domain.ReleaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;


@Entity
@Table(name = "customer_alert_setting")
@NoArgsConstructor(access = PROTECTED)
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
    @Convert(converter = ReleaseTimeConverter.class)
    private ReleaseTime startTime;

    @Column(name = "dnd_end_time", columnDefinition = "TIME", nullable = false)
    @Convert(converter = ReleaseTimeConverter.class)
    private ReleaseTime endTime;

    @Builder
    private CustomerAlertSettingEntity(Long customerId, boolean active, DayOfWeekSet days, ReleaseTime startTime, ReleaseTime endTime) {
        this.customerId = customerId;
        this.active = active;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static CustomerAlertSettingEntity fromDomain(Long customerId, DoNotDisturb dnd) {
        return CustomerAlertSettingEntity.builder()
                .customerId(customerId)
                .active(dnd.isActive())
                .days(dnd.getDays())
                .startTime(dnd.getStartTime())
                .endTime(dnd.getEndTime())
                .build();
    }

    public DoNotDisturb toDomain() {
        return DoNotDisturb.builder()
                .days(days)
                .startTime(startTime)
                .endTime(endTime)
                .active(active)
                .build();
    }
}
