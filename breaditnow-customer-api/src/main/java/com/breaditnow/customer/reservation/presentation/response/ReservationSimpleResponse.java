package com.breaditnow.customer.reservation.presentation.response;

import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryEntity;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

public record ReservationSimpleResponse(
        ReservationInfoResponse reservationInfo,
        List<ReservationProductResponse> reservationProduct,
        ReservationSummaryResponse reservationSummary
) {
    public static ReservationSimpleResponse from(BakeryEntity bakeryEntity, ReservationEntity reservationEntity) {
        ReservationInfoResponse reservationInfoResponse = ReservationInfoResponse.of(
                bakeryEntity.getName(),
                reservationEntity.getReservationTime().toLocalDate(),
                reservationEntity.getReservationStatus()
        );

        List<ReservationProductResponse> reservationProductResponses = reservationEntity.getReservationItems().stream()
                .map(item -> ReservationProductResponse.of(
                        item.getProductImageUrl(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getProductPrice(),
                        item.getTotalPrice())
                )
                .toList();

        ReservationSummaryResponse reservationSummaryResponse = ReservationSummaryResponse.of(
                reservationEntity.getReservationItems().size(),
                reservationEntity.getTotalPrice()
        );

        return new ReservationSimpleResponse(reservationInfoResponse, reservationProductResponses, reservationSummaryResponse);
    }

    public record ReservationInfoResponse(
            String bakeryName,
            LocalDate reservationTime,
            ReservationStatus reservationStatus
    ) {
        public static ReservationInfoResponse of(String bakeryName, LocalDate reservationTime, ReservationStatus reservationStatus) {
            return new ReservationInfoResponse(bakeryName, reservationTime, reservationStatus);
        }
    }

    public record ReservationProductResponse(
            String productImageUrl,
            String productName,
            Integer quantity,
            Integer unitPrice,
            Integer totalPrice
    ) {
        public static ReservationProductResponse of(String productImageUrl, String productName, Integer quantity, Integer unitPrice, Integer totalPrice) {
            return new ReservationProductResponse(productImageUrl, productName, quantity, unitPrice, totalPrice);
        }
    }

    public record ReservationSummaryResponse(
            Integer totalQuantity,
            Integer totalPrice
    ) {
        public static ReservationSummaryResponse of(Integer totalQuantity, Integer totalPrice) {
            return new ReservationSummaryResponse(totalQuantity, totalPrice);
        }
    }
}