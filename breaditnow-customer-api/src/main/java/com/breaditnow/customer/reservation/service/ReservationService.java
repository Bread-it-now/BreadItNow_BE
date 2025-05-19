package com.breaditnow.customer.reservation.service;

import com.breaditnow.customer.reservation.controller.req.ReservationCancelRequest;
import com.breaditnow.customer.reservation.controller.req.ReservationRequest;
import com.breaditnow.customer.reservation.controller.res.ReservationCancelResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.breaditnow.domain.domain.reservation.entity.ReservationSequence;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;
import com.breaditnow.domain.domain.reservation.repository.ReservationProductRepository;
import com.breaditnow.domain.domain.reservation.repository.ReservationRepository;
import com.breaditnow.domain.domain.reservation.repository.ReservationSequenceRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus.WAITING;
import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationProductRepository reservationProductRepository;
    private final ReservationSequenceRepository reservationSequenceRepository;
    private final ProductRepository productRepository;
    private final BakeryRepository bakeryRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public ReservationResponse createReservation(Long customerId, ReservationRequest request) {
        Customer customer = customerRepository.getById(customerId);
        Bakery bakery = bakeryRepository.getById(request.bakeryId());

        int totalPrice = request.reservationProducts().stream()
                .mapToInt(req -> {
                    Product product = productRepository.getById(req.productId());

                    if (!product.getBakery().getId().equals(request.bakeryId())) {
                        throw new DomainException(BAKERY_MISMATCH);
                    }

                    if (!product.isActive() || product.isHidden()) {
                        throw new DomainException(PRODUCT_CANNOT_ORDER);
                    }

                    if (product.getStock() < req.quantity()) {
                        throw new DomainException(RESERVATION_OUT_OF_STOCK);
                    }

                    return product.getPrice() * req.quantity();
                })
                .sum();

        int todayNumber = generateTodaySequenceNumber();

        Reservation tempReservation = Reservation.builder()
                .customer(customer)
                .bakery(bakery)
                .status(WAITING)
                .totalPrice(totalPrice)
                .pickupDeadline(LocalDateTime.now().plusMinutes(30))
                .reservationNumber(todayNumber)
                .build();

        final Reservation savedReservation = reservationRepository.save(tempReservation);

        List<ReservationProduct> reservationProducts = request.reservationProducts().stream()
                .map(req -> {
                    Product product = productRepository.getById(req.productId());

                    return ReservationProduct.builder()
                            .reservation(savedReservation)
                            .product(product)
                            .quantity(req.quantity())
                            .unitPrice(product.getPrice())
                            .productName(product.getName())
                            .productImage(product.getImage())
                            .build();
                })
                .collect(Collectors.toList());

        reservationProductRepository.saveAll(reservationProducts);

        return ReservationResponse.of(savedReservation, reservationProducts);
    }

    public ReservationPageResponse getReservations(Long customerId, ReservationRequestStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservationsPage = reservationRepository.getReservationsByStatus(customerId, status, pageable);
        return ReservationPageResponse.of(reservationsPage);
    }

    public ReservationDetailResponse getReservationDetail(Long customerId, Long reservationId) {
        Reservation reservation = reservationRepository.getByIdAndCustomerId(reservationId, customerId);

        List<ReservationProduct> reservationProducts = reservationProductRepository.findByReservationId(reservationId);

        return ReservationDetailResponse.of(reservation, reservation.getBakery(), reservationProducts);
    }

    @Transactional
    public ReservationCancelResponse cancelReservation(Long customerId, Long reservationId, ReservationCancelRequest request) {
        Reservation reservation = reservationRepository.getByIdAndCustomerId(reservationId, customerId);

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new DomainException(RESERVATION_ALREADY_CANCELLED);
        }

        reservation.updateStatus(ReservationStatus.CANCELLED);
        reservation.updateCancelReason(request.reason());

        reservationRepository.save(reservation);

        return ReservationCancelResponse.of(reservation);
    }

    private int generateTodaySequenceNumber() {
        LocalDate today = LocalDate.now();

        ReservationSequence sequence = reservationSequenceRepository
                .findById(today)
                .orElseGet(() -> ReservationSequence.builder()
                        .date(today)
                        .currentNumber(0)
                        .build());

        int number = sequence.nextNumber();
        reservationSequenceRepository.save(sequence);
        return number;
    }
}
