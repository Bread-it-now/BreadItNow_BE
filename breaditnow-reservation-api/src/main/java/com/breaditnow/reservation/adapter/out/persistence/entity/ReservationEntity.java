package com.breaditnow.reservation.adapter.out.persistence.entity;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.jpa.BaseEntity;
import com.breaditnow.reservation.domain.model.Orderer;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationState;
import com.breaditnow.reservation.domain.model.ReservedBakery;
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
@Table(name = "reservation")
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
    private String bakeryName;
    private String bakeryAddress;
    private String bakeryPhone;
    private String bakeryProfileImageUrl;

    private Long ordererId;
    private String ordererPhoneNumber;
    private String ordererNickname;

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
        ReservedBakery reservedBakery = reservation.getReservedBakery();

        return ReservationEntity.builder()
                .id(reservation.getReservationId())
                .cancellationReason(reservation.getReservationState().getCancelReason())
                .reservationStatus(reservation.getReservationState().getReservationStatus())
                .reservationNumber(reservation.getReservationNumber())
                .totalPrice(reservation.getTotalPrice().getAmount())
                .reservationItems(reservation.getReservationProducts().stream()
                        .map(ReservationItemEmbeddable::from)
                        .toList())
                .ordererId(reservation.getOrderer().getCustomerId())
                .ordererPhoneNumber(reservation.getOrderer().getPhoneNumber())
                .ordererNickname(reservation.getOrderer().getNickname())
                .bakeryId(reservedBakery.bakeryId())
                .bakeryName(reservedBakery.name())
                .bakeryAddress(reservedBakery.address())
                .bakeryPhone(reservedBakery.phone())
                .bakeryProfileImageUrl(reservedBakery.profileImageUrl())
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
                .reservedBakery(new ReservedBakery(this.bakeryId, this.bakeryName, this.bakeryAddress, this.bakeryPhone, this.bakeryProfileImageUrl))
                .orderer(new Orderer(this.ordererId, this.ordererNickname, this.ordererPhoneNumber))
                .totalPrice(new Money(this.totalPrice))
                .reservationNumber(this.reservationNumber)
                .reservationTime(getModifiedAt())
                .build();
    }
}
