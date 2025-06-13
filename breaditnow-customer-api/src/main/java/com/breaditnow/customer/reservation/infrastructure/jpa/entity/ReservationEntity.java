package com.breaditnow.customer.reservation.infrastructure.jpa.entity;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.reservation.domain.Reservation;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.vo.ReservationItemEmbeddable;
import com.breaditnow.customer.reservation.infrastructure.jpa.vo.OrdererEmbeddable;
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
@Table(name = "reservations")
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
    private Long reservationNumber;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "reservation_products", joinColumns = @JoinColumn(name = "reservation_id"))
    @OrderColumn(name = "line_idx")
    private List<ReservationItemEmbeddable> reservationItems;

    @Embedded
    private OrdererEmbeddable orderer;

    @Enumerated(STRING)
    private ReservationStatus reservationStatus;
    private String cancellationReason;

    private LocalDateTime reservationTime;
    private LocalDateTime pickupDeadLine;

    private Integer totalPrice;

    public static ReservationEntity from(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getReservationId())
                .cancellationReason(reservation.getReservationState().getCancelReason())
                .reservationStatus(reservation.getReservationState().getReservationStatus())
                .reservationNumber(reservation.getReservationNumber())
                .reservationTime(reservation.getReservationTime())
                .totalPrice(reservation.getTotalPrice().getAmount())
                .reservationItems(reservation.getReservationItems().stream()
                        .map(ReservationItemEmbeddable::from)
                        .toList())
                .orderer(OrdererEmbeddable.from(reservation.getOrderer()))
                .bakeryId(reservation.getBakeryId())
                .build();
    }

    public Reservation toDomain() {
        return Reservation.builder()
                .reservationId(this.id)
                .reservationItems(this.reservationItems.stream()
                        .map(ReservationItemEmbeddable::toDomain)
                        .toList()
                )
                .reservationStatus(this.reservationStatus)
                .bakeryId(this.bakeryId)
                .orderer(this.orderer.toDomain())
                .totalPrice(new Money(this.totalPrice))
                .reservationTime(this.reservationTime)
                .reservationNumber(this.reservationNumber)
                .build();
    }
}
