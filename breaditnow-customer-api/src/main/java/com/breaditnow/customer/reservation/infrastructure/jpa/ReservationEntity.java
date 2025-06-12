package com.breaditnow.customer.reservation.infrastructure.jpa;

import com.breaditnow.customer.reservation.domain.Reservation;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reservations")
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

    private LocalDateTime reservationTime;

    private Integer totalPrice;

    public static ReservationEntity from(Reservation reservation) {
        return new ReservationEntity(
                null,
                reservation.getBakeryId(),
                reservation.getReservationNumber(),
                reservation.getReservationItems().stream()
                        .map(ReservationItemEmbeddable::from)
                        .toList(),
                OrdererEmbeddable.from(reservation.getOrderer()),
                reservation.getReservationStatus(),
                reservation.getReservationTime(),
                reservation.getTotalPrice().getAmount()
        );
    }
}
