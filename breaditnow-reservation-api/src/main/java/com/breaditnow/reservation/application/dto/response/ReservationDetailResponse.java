package com.breaditnow.reservation.application.dto.response;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;

import java.util.List;

public record ReservationDetailResponse(
        Long reservationId,
        Long reservationNumber,
        String reservationDate,
        String pickupDeadline,
        ReservationStatus status,
        Integer totalPrice,
        String consumerNickname,
        String consumerPhone,
        List<ReservationItemResponse> reservationItems
) {

    public static ReservationDetailResponse from(Reservation reservation){
        List<ReservationItemResponse> items = reservation.getReservationProducts().stream()
                .map(ReservationItemResponse::from)
                .toList();

        return new ReservationDetailResponse(
                reservation.getReservationId(),
                reservation.getReservationNumber(),
                reservation.getReservationDate(),
                reservation.getPickupDeadline(),
                reservation.getReservationState().getReservationStatus(),
                reservation.getTotalPrice().getAmount(),
                reservation.getOrderer().getNickname(),
                reservation.getOrderer().getPhoneNumber(),
                items
        );
    }

    public record ReservationItemResponse(
            Long productId,
            String productName,
            Integer quantity,
            Integer unitPrice,
            Integer totalPrice,
            String productImageUrl
    ) {
        public static ReservationItemResponse from(ReservationProduct reservationProducts) {
            return new ReservationItemResponse(
                    reservationProducts.getProductId(),
                    reservationProducts.getProductName(),
                    reservationProducts.getQuantity(),
                    reservationProducts.getPrice().getAmount(),
                    reservationProducts.getTotalPrice().getAmount(),
                    reservationProducts.getProductImage()
            );
        }
    }
}
