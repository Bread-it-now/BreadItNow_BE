package com.breaditnow.reservation.domain;

import com.breaditnow.reservation.application.dto.event.OperatingStatus;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "bakery_operational_info")
public class BakeryOperationalInfo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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
}
