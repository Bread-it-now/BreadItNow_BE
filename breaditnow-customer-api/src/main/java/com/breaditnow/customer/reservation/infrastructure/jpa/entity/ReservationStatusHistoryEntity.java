package com.breaditnow.customer.reservation.infrastructure.jpa.entity;

import com.breaditnow.customer.reservation.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "reservation_status_history")
@NoArgsConstructor(access = PROTECTED)
public class ReservationStatusHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    private Long id;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Enumerated(STRING)
    private ReservationStatus oldStatus;

    @Enumerated(STRING)
    private ReservationStatus newStatus;

    private LocalDateTime changeAt;

    public ReservationStatusHistoryEntity(Long reservationId, ReservationStatus oldStatus, ReservationStatus newStatus) {
        this.reservationId = reservationId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changeAt = LocalDateTime.now();
    }
}
