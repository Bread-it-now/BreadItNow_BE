package com.breaditnow.reservation.adapter.out.persistence.entity;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.jpa.BaseEntity;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reservation", indexes = {
        @Index(name = "idx_bakery_status_modified", columnList = "bakery_id, reservation_status, modified_at"),
        @Index(name = "idx_orderer_modified", columnList = "orderer_id, modified_at"),
        @Index(name = "idx_reservation_number", columnList = "reservation_number")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationEntity extends BaseEntity {
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
    private Integer totalPrice;

    public static ReservationEntity from(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getReservationId())
                .cancellationReason(reservation.getReservationState().getCancelReason())
                .reservationStatus(reservation.getReservationState().getReservationStatus())
                .reservationNumber(reservation.getReservationNumber())
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
                .reservationState(new ReservationState(this.reservationStatus, this.cancellationReason))
                .bakeryId(this.bakeryId)
                .customerId(this.ordererId)
                .totalPrice(new Money(this.totalPrice))
                .reservationNumber(this.reservationNumber)
                .build();
    }
}
