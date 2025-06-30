package com.breaditnow.reservation.adapter.out.persistence;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.out.persistence.entity.ReservationEntity;
import com.breaditnow.reservation.adapter.out.persistence.repository.JpaReservationRepository;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.breaditnow.common.domain.ReservationStatus.APPROVED;
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
    public List<Reservation> findByCustomerId(Long customerId) {
        return jpaRepository.findAllByOrdererIdWithItemsOrderByModifiedAtDesc(customerId).stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Page<Reservation> findByCustomerId(Long ordererId, Pageable pageable, ReservationStatus status) {
        return jpaRepository.findByOrdererId(ordererId, pageable, status)
                .map(ReservationEntity::toDomain);
    }

    @Override
    public List<Reservation> findByBakeryId(Long bakeryId) {
        return jpaRepository.findAllByBakeryIdWithItemsOrderByModifiedAtDesc(bakeryId).stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        ReservationEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Long getNextReservationNumber(Long bakeryId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(MAX);

        return jpaRepository.findFirstByBakeryIdAndReservationStatusAndModifiedAtBetweenOrderByReservationNumberDesc(bakeryId, APPROVED, startOfDay, endOfDay)
                .map(ReservationEntity::getReservationNumber)
                .map(lastNumber -> lastNumber + 1)
                .orElse(1L);
    }
}
