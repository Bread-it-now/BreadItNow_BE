package com.breaditnow.reservation.infrastructure.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "reservation_view")
public class ReservationViewEntity {
    @Id
    private Long reservationId;

    @Column(nullable = false)
    private Long bakeryId;

    @Column(nullable = false)
    private Long customerId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String productInfoJson;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @Column(nullable = false)
    private String status;

    private String reservationNumber;
    private LocalDateTime pickupDeadline;

    public void updateOnApproval(String status, String reservationNumber, LocalDateTime pickupDeadline) {
        this.status = status;
        this.reservationNumber = reservationNumber;
        this.pickupDeadline = pickupDeadline;
    }
}

