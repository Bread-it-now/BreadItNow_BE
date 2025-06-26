package com.breaditnow.reservation.application;

import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;
import com.breaditnow.reservation.application.port.in.ReservationViewSynchronizationUseCase;
import com.breaditnow.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewEntity;
import com.breaditnow.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationViewSynchronizationService implements ReservationViewSynchronizationUseCase {
    private final ReservationViewRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    @SneakyThrows
    public void createReservationView(ReservationCreatedEvent event) {
        ReservationViewEntity entity = ReservationViewEntity.builder()
                .reservationId(event.reservationId())
                .bakeryId(event.bakeryId())
                .customerId(event.customerId())
                .productInfoJson(objectMapper.writeValueAsString(event.products()))
                .totalPrice(event.totalPrice().amount())
                .reservationTime(event.reservationTime())
                .status(event.status())
                .build();

        repository.save(entity);
    }
}