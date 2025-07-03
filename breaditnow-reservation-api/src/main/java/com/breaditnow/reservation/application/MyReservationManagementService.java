package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.request.MyReservationCancelRequest;
import com.breaditnow.reservation.application.event.ReservationStatusChangedEvent;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.MyReservationCancelUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationEventPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.CUSTOMER;

@Service
@RequiredArgsConstructor
@Transactional
public class MyReservationManagementService implements MyReservationCancelUseCase {
    private final ReservationProvider reservationProvider;
    private final ReservationValidator reservationValidator;
    private final ReservationRepository reservationRepository;
    private final ReservationEventPort reservationEventPort;
    private final BakeryProvider bakeryProvider;

    @Override
    @Authorize(CUSTOMER)
    public void cancelReservation(AuthenticatedUser user, Long reservationId, MyReservationCancelRequest request) {
        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateReservationBelongsToCustomer(reservation, user.userId());

        reservation.cancel(request.reason());
        Reservation savedReservation = reservationRepository.save(reservation);

        BakeryInfo bakeryInfo = bakeryProvider.provide(reservation.getReservedBakery().bakeryId());
        ReservationStatusChangedEvent reservationCreatedEvent = ReservationStatusChangedEvent.from(savedReservation, CUSTOMER, request.reason(), bakeryInfo.ownerId());
        reservationEventPort.publish(reservationCreatedEvent);
    }
}
