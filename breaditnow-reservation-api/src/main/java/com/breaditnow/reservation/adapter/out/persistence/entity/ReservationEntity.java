package com.breaditnow.reservation.adapter.out.persistence.entity;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.common.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reservation")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private Long bakeryId;
    private Long ordererId;
    private Long reservationNumber;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "reservation_products", joinColumns = @JoinColumn(name = "reservation_id"))
    @OrderColumn(name = "line_idx")
    private List<ReservationItemEmbeddable> reservationItems;

    @Enumerated(STRING)
    private ReservationStatus reservationStatus;
    private String cancellationReason;

    private LocalDateTime reservationTime;
    private LocalDateTime approvalTime;

    private Integer totalPrice;

    public static ReservationEntity from(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getReservationId())
                .cancellationReason(reservation.getReservationState().getCancelReason())
                .reservationStatus(reservation.getReservationState().getReservationStatus())
                .reservationNumber(reservation.getReservationNumber())
                .approvalTime(reservation.getApprovalTime())
                .approvalTime(reservation.getApprovalTime())
                .reservationTime(reservation.getReservationTime())
                .totalPrice(reservation.getTotalPrice().getAmount())
                .reservationItems(reservation.getReservationProducts().stream()
                        .map(ReservationItemEmbeddable::from)
                        .toList())
                .ordererId(reservation.getCustomerId())
                .bakeryId(reservation.getBakeryId())
                .build();
    }

    public Reservation toDomain() {
        return Reservation.builder()
                .reservationId(this.id)
                .reservationProducts(this.reservationItems.stream()
                        .map(ReservationItemEmbeddable::toDomain)
                        .toList()
                )
                .reservationStatus(this.reservationStatus)
                .bakeryId(this.bakeryId)
                .customerId(this.ordererId)
                .totalPrice(new Money(this.totalPrice))
                .reservationTime(this.reservationTime)
                .reservationNumber(this.reservationNumber)
                .approvalTime(this.approvalTime)
                .build();
    }
}
