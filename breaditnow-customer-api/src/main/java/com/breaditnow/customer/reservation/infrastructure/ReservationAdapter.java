package com.breaditnow.customer.reservation.infrastructure;

import com.breaditnow.customer.reservation.domain.Reservation;
import com.breaditnow.customer.reservation.domain.port.LoadReservationPort;
import com.breaditnow.customer.reservation.domain.port.SaveReservationPort;
import com.breaditnow.customer.reservation.infrastructure.jpa.JpaReservationRepository;
import com.breaditnow.customer.reservation.infrastructure.jpa.QueryReservationRepository;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationEntity;
import com.breaditnow.customer.reservation.infrastructure.jpa.dto.ReservationWithBakery;
import com.breaditnow.customer.reservation.presentation.response.ReservationDetailResponse;
import com.breaditnow.customer.reservation.presentation.response.ReservationSimpleResponse;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.RESERVATION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ReservationAdapter implements LoadReservationPort, SaveReservationPort {
    private final JpaReservationRepository jpaReservationRepository;
    private final QueryReservationRepository queryReservationRepository;

    @Override
    public Long save(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        return jpaReservationRepository.save(entity).getId();
    }

    public ReservationDetailResponse getReservationDetail(Long customerId, Long reservationId) {
        ReservationWithBakery reservationWithBakery = queryReservationRepository.getReservation(customerId, reservationId)
                .orElseThrow(() -> new DomainException(RESERVATION_NOT_FOUND));

        return ReservationDetailResponse.from(reservationWithBakery.bakery(), reservationWithBakery.reservation());
    }

    public ReservationSimpleResponse getReservationSimple(Long customerId, Long reservationId) {
        ReservationWithBakery reservationWithBakery = queryReservationRepository.getReservation(customerId, reservationId)
                .orElseThrow(() -> new DomainException(RESERVATION_NOT_FOUND));

        return ReservationSimpleResponse.from(reservationWithBakery.bakery(), reservationWithBakery.reservation());
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        return jpaReservationRepository.findById(reservationId)
                .map(ReservationEntity::toDomain)
                .orElseThrow(() -> new DomainException(RESERVATION_NOT_FOUND));

    }
}
