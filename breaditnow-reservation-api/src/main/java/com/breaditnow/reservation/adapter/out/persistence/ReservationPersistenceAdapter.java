package com.breaditnow.reservation.adapter.out.persistence;

import com.breaditnow.reservation.adapter.out.persistence.entity.ReservationEntity;
import com.breaditnow.reservation.adapter.out.persistence.repository.JpaReservationRepository;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalTime.MAX;

@Repository
@RequiredArgsConstructor
public class ReservationPersistenceAdapter implements ReservationRepository {
    private final JpaReservationRepository jpaRepository;

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return jpaRepository.findById(reservationId)
                .map(ReservationEntity::toDomain);
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        ReservationEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Reservation> findLastOfBakeryForToday(Long bakeryId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(MAX);

        return jpaRepository.findFirstByBakeryIdAndApprovalTimeBetweenOrderByReservationNumberDesc(bakeryId, startOfDay, endOfDay)
                .map(ReservationEntity::toDomain);
    }
}
