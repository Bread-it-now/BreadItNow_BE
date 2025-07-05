package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.OrdererInfo;
import com.breaditnow.reservation.application.dto.request.MyReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.MyReservationCreateRequest;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;
import com.breaditnow.reservation.application.event.*;
import com.breaditnow.reservation.application.factory.ProductFactory;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.CustomerProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Orderer;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.model.ReservedBakery;
import com.breaditnow.reservation.domain.port.in.ReservationApproveUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationCancelUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationCreateUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationPartialApproveUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationCommandService implements ReservationCreateUseCase, ReservationApproveUseCase, ReservationPartialApproveUseCase, ReservationCancelUseCase {
    private final ReservationRepository reservationRepository;
    private final ReservationProvider reservationProvider;
    private final BakeryProvider bakeryProvider;
    private final CustomerProvider customerProvider;
    private final ProductFactory productFactory;
    private final BakeryValidator bakeryValidator;
    private final ReservationValidator reservationValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Authorize(CUSTOMER)
    public Long createReservation(AuthenticatedUser user, MyReservationCreateRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(request.bakeryId());
        bakeryValidator.validateBakeryForReservation(bakeryInfo);
        ReservedBakery reservedBakery = ReservedBakery.create(bakeryInfo);

        List<ReservationProduct> reservationProducts = productFactory.createFrom(request, bakeryInfo.bakeryId());
        OrdererInfo ordererInfo = customerProvider.provide(user.userId());
        Orderer orderer = Orderer.create(ordererInfo);

        Reservation reservation = new Reservation(orderer, reservedBakery, reservationProducts);
        Reservation savedReservation = reservationRepository.save(reservation);

        eventPublisher.publishEvent(new ReservationCreatedEvent(savedReservation, bakeryInfo.ownerId()));
        return savedReservation.getReservationId();
    }

    @Override
    @Authorize(OWNER)
    public void approveReservation(AuthenticatedUser user, Long bakeryId, Long reservationId) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        Reservation reservation = reservationProvider.provide(reservationId, bakeryId);
        UserIdentifier initiator = new UserIdentifier(user.userId(), OWNER);

        eventPublisher.publishEvent(new ReservationApprovedEvent(reservation, initiator));
    }

    @Override
    @Authorize(OWNER)
    public void partialApproveReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationPartialApproveRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        bakeryValidator.validateBakeryForReservation(bakeryInfo);

        List<ReservationProduct> reservationProducts = productFactory.createFrom(request, bakeryInfo.bakeryId());
        UserIdentifier initiator = new UserIdentifier(user.userId(), OWNER);

        eventPublisher.publishEvent(new ReservationPartiallyApprovedEvent(reservationId, initiator, reservationProducts, request.reason()));
    }

    @Override
    @Authorize(OWNER)
    public void cancelReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationCancelRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        Reservation reservation = reservationProvider.provide(reservationId, bakeryId);
        UserIdentifier initiator = new UserIdentifier(user.userId(), OWNER);
        handleCancellation(reservation, initiator, bakeryInfo.ownerId(), request.reason());
    }

    @Override
    @Authorize(CUSTOMER)
    public void cancelReservation(AuthenticatedUser user, Long reservationId, MyReservationCancelRequest request) {
        Reservation reservation = reservationProvider.provide(reservationId);
        BakeryInfo bakeryInfo = bakeryProvider.provide(reservation.getReservedBakery().bakeryId());
        reservationValidator.validateReservationBelongsToCustomer(reservation, user.userId());
        UserIdentifier initiator = new UserIdentifier(user.userId(), CUSTOMER);
        handleCancellation(reservation, initiator, bakeryInfo.ownerId(), request.reason());
    }

    private void handleCancellation(Reservation reservation, UserIdentifier initiator, Long ownerId, String reason) {
        boolean isCompletedReservation = reservation.getReservationState().isCompleted();

        if (isCompletedReservation) {
            eventPublisher.publishEvent(new StockRestoreRequiredEvent(reservation, initiator, reason));
        } else {
            reservation.cancel(reason);
            reservationRepository.save(reservation);
            eventPublisher.publishEvent(new CancellationNotificationRequiredEvent(reservation, initiator, ownerId, reason));
        }
    }
}
