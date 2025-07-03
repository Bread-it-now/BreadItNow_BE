package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;
import com.breaditnow.reservation.application.event.ReservationStatusChangedEvent;
import com.breaditnow.reservation.application.factory.ProductFactory;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.in.ReservationApproveUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationCancelUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationPartialApproveUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationEventPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationManagementService implements ReservationApproveUseCase, ReservationPartialApproveUseCase, ReservationCancelUseCase {
    private final ReservationRepository reservationRepository;
    private final ReservationProvider reservationProvider;
    private final ProductFactory productFactory;
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final ReservationEventPort reservationEventPort;

    @Override
    @Authorize(OWNER)
    public void approveReservation(AuthenticatedUser user, Long bakeryId, Long reservationId) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);

        Reservation reservation = reservationProvider.provide(reservationId, bakeryId);

        Long newReservationNumber = reservationRepository.getNextReservationNumber(bakeryId);
        reservation.approve(newReservationNumber);
        reservationRepository.save(reservation);

        reservationEventPort.publish(ReservationStatusChangedEvent.from(reservation, OWNER, null, user.userId()));
    }

    @Override
    @Authorize(OWNER)
    public void cancelReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationCancelRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);

        Reservation reservation = reservationProvider.provide(reservationId, bakeryId);

        reservation.cancel(request.reason());
        reservationRepository.save(reservation);

        reservationEventPort.publish(ReservationStatusChangedEvent.from(reservation, OWNER, request.reason(), user.userId()));
    }

    @Override
    @Authorize(OWNER)
    public void partialApproveReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationPartialApproveRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);

        bakeryValidator.validateOwner(bakeryInfo, user);
        bakeryValidator.validateBakeryForReservation(bakeryInfo);

        List<ReservationProduct> reservationProducts = productFactory.createFrom(request, bakeryInfo.bakeryId());

        Reservation reservation = reservationProvider.provide(reservationId, bakeryId);
        Long newReservationNumber = reservationRepository.getNextReservationNumber(bakeryId);

        reservation.partialApprove(reservationProducts, newReservationNumber);
        reservationRepository.save(reservation);

        reservationEventPort.publish(ReservationStatusChangedEvent.from(reservation, OWNER, request.reason(), user.userId()));
    }
}
