//package com.breaditnow.owner.domain.reservation.service;
//
//import com.breaditnow.domain.domain.reservation.entity.Reservation;
//import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
//import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
//import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;
//import com.breaditnow.domain.domain.reservation.enumerate.ReservationUpdateStatus;
//import com.breaditnow.domain.domain.reservation.repository.ReservationProductRepository;
//import com.breaditnow.domain.domain.reservation.repository.ReservationRepository;
//import com.breaditnow.owner.domain.reservation.controller.req.ReservationItemUpdateRequest;
//import com.breaditnow.owner.domain.reservation.controller.req.ReservationStatusUpdateRequest;
//import com.breaditnow.owner.domain.reservation.controller.res.ReservationDetailResponse;
//import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
//import com.breaditnow.owner.domain.reservation.controller.res.ReservationStatusUpdateResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class ReservationService {
//
//    private final ReservationRepository reservationRepository;
//    private final ReservationProductRepository reservationProductRepository;
//
//    public ReservationPageResponse getReservationsForOwner(Long ownerId, ReservationRequestStatus status, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Reservation> reservations = reservationRepository.getReservationsByOwnerAndStatus(ownerId, status, pageable);
//        return ReservationPageResponse.of(reservations);
//    }
//
//    public ReservationDetailResponse getReservationDetailForOwner(Long ownerId, Long reservationId) {
//        Reservation reservation = reservationRepository.getByIdAndOwnerId(reservationId, ownerId);
//        List<ReservationProduct> products = reservationProductRepository.findByReservationId(reservationId);
//        return ReservationDetailResponse.of(reservation, reservation.getBakery(), products);
//    }
//
//    @Transactional
//    public ReservationStatusUpdateResponse updateReservationStatus(Long ownerId, Long reservationId, ReservationStatusUpdateRequest request) {
//
//        Reservation reservation = reservationRepository.getByIdAndOwnerId(reservationId, ownerId);
//
//        if (request.status() == ReservationUpdateStatus.OWNER_REJECTED) {
//            reservation.updateStatus(ReservationStatus.OWNER_REJECTED);
//            reservation.updateCancelReason(request.reason());
//            reservationRepository.save(reservation);
//
//            return ReservationStatusUpdateResponse.of(reservation.getId(), reservation.getStatus().name());
//        }
//
//        if (request.status() == ReservationUpdateStatus.APPROVED) {
//            boolean isPartiallyApproved = false;
//
//            if (request.reservationItems() != null) {
//                Map<Long, Integer> requestItemMap = request.reservationItems().stream()
//                        .collect(Collectors.toMap(ReservationItemUpdateRequest::productId, ReservationItemUpdateRequest::quantity));
//
//                for (ReservationProduct product : reservation.getReservationProducts()) {
//                    int requestedQuantity = requestItemMap.getOrDefault(product.getProduct().getId(), product.getQuantity());
//
//                    if (requestedQuantity < product.getQuantity()) {
//                        isPartiallyApproved = true;
//                        product.updateQuantity(requestedQuantity);
//                    }
//                }
//            }
//
//            reservation.updateStatus(isPartiallyApproved
//                    ? ReservationStatus.PARTIAL_APPROVED
//                    : ReservationStatus.APPROVED);
//
//            reservationRepository.save(reservation);
//        }
//
//        return ReservationStatusUpdateResponse.of(reservation.getId(), reservation.getStatus().name());
//    }
//
//}
