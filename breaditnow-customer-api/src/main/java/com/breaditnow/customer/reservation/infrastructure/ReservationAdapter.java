package com.breaditnow.customer.reservation.infrastructure;

import com.breaditnow.customer.reservation.domain.Reservation;
import com.breaditnow.customer.reservation.domain.port.LoadReservationPort;
import com.breaditnow.customer.reservation.domain.port.SaveReservationPort;
import com.breaditnow.customer.reservation.infrastructure.jpa.JpaReservationRepository;
import com.breaditnow.customer.reservation.infrastructure.jpa.QueryReservationRepository;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationEntity;
import com.breaditnow.customer.reservation.presentation.response.ReservationDetailResponse;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.breaditnow.domain.global.exception.DomainErrorCode.RESERVATION_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ReservationAdapter implements LoadReservationPort, SaveReservationPort {
    private final JpaReservationRepository jpaReservationRepository;
    private final QueryReservationRepository queryReservationRepository;

    @Override
    public void save(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        jpaReservationRepository.save(entity);
    }

    @Override
    public Long findLatestReservationNumberForBakeryToday(Long bakeryId, LocalDate today) {
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return jpaReservationRepository.findLatestReservationNumberForBakeryToday(bakeryId, start, end)
                .orElse(0L);
    }

    public ReservationDetailResponse getReservationDetail(Long customerId, Long reservationId) {
        return queryReservationRepository.getReservationDetail(customerId, reservationId)
                .orElseThrow(() -> new DomainException(RESERVATION_NOT_FOUND));
    }
}
