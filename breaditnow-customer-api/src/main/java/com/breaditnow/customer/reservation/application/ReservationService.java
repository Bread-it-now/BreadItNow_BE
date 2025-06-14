package com.breaditnow.customer.reservation.application;

import com.breaditnow.customer.common.domain.Events;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.port.LoadProductPort;
import com.breaditnow.customer.reservation.application.request.CancelReasonRequest;
import com.breaditnow.customer.reservation.application.request.ReservationRequest;
import com.breaditnow.customer.reservation.domain.Reservation;
import com.breaditnow.customer.reservation.domain.ReservationProducts;
import com.breaditnow.customer.reservation.domain.event.ReservationStatusChangedEvent;
import com.breaditnow.customer.reservation.domain.port.LoadReservationPort;
import com.breaditnow.customer.reservation.domain.port.SaveReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final LoadProductPort loadProductPort;
    private final SaveReservationPort saveReservationPort;
    private final LoadReservationPort loadReservationPort;

    @Transactional
    public Long createReservation(Long ordererId, ReservationRequest request) {
        List<ReservationProducts> reservationProducts = new ArrayList<>();

        request.reservationProducts().forEach(reservationProduct -> {
            Product product = loadProductPort.getProduct(reservationProduct.productId());
            product.validateBelongsToBakery(request.bakeryId());
            reservationProducts.add(new ReservationProducts(product.getId(), product.getName(), product.getImageUrl(), product.getPrice(), reservationProduct.quantity()));
        });

        Reservation reservation = new Reservation(ordererId, request.bakeryId(), reservationProducts);
        Long reservationId = saveReservationPort.save(reservation);
        Events.publish(new ReservationStatusChangedEvent(reservationId, null, reservation.getReservationState().getReservationStatus()));
        return reservationId;
    }

    @Transactional
    public void cancelReservation(Long customerId, Long reservationId, CancelReasonRequest request) {
        Reservation reservation = loadReservationPort.getReservation(reservationId);
        reservation.cancel(customerId, request.reason());
        saveReservationPort.save(reservation);
    }
}
