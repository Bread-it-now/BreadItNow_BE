package com.breaditnow.reservation.domain;

import com.breaditnow.reservation.application.dto.event.OperatingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "bakery_info")
public class BakeryInfo {
    @Id
    private Long bakeryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperatingStatus operatingStatus;

    @Column(nullable = false)
    private boolean deleted;

    public void updateOperatingStatus(OperatingStatus newStatus) {
        this.operatingStatus = newStatus;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isReservable() {
        return operatingStatus == OperatingStatus.OPEN && !deleted;
    }
}
