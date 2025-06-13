package com.breaditnow.customer.reservation.application;

import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.port.LoadProductPort;
import com.breaditnow.customer.reservation.application.request.ReservationRequest;
import com.breaditnow.customer.reservation.domain.Orderer;
import com.breaditnow.customer.reservation.domain.Reservation;
import com.breaditnow.customer.reservation.domain.ReservationItem;
import com.breaditnow.customer.reservation.domain.port.LoadOrdererPort;
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
    private final LoadOrdererPort loadOrdererPort;
    private final SaveReservationPort saveReservationPort;

    @Transactional
    public Long createReservation(Long ordererId, ReservationRequest request) {
        List<ReservationItem> reservationItems = new ArrayList<>();
        request.reservationProducts().forEach(orderProduct -> {
            Product product = loadProductPort.getProduct(orderProduct.productId());
            product.validateBelongsToBakery(request.bakeryId());
            reservationItems.add(new ReservationItem(product.getId(), product.getName(), product.getImageUrl(), product.getPrice(), orderProduct.quantity()));
        });
        Orderer orderer = loadOrdererPort.getOrderer(ordererId);
        Reservation reservation = new Reservation(orderer, request.bakeryId(), reservationItems);
        return saveReservationPort.save(reservation);
    }
}
