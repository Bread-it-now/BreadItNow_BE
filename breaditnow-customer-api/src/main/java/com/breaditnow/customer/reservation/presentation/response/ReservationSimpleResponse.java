package com.breaditnow.customer.reservation.presentation.response;

import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryEntity;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.entity.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

public record ReservationSimpleResponse(
        ReservationInfoResponse reservationInfo,
        List<ReservationProductResponse> reservationProducts,
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
            LocalDate reservationDate,
            ReservationStatus reservationStatus
    ) {
        public static ReservationInfoResponse of(String bakeryName, LocalDate reservationDate, ReservationStatus reservationStatus) {
            return new ReservationInfoResponse(bakeryName, reservationDate, reservationStatus);
        }
    }

    public record ReservationProductResponse(
            String productImageUrl,
            String productName,
            Integer productQuantity,
            Integer productUnitPrice,
            Integer productTotalPrice
    ) {
        public static ReservationProductResponse of(String productImageUrl, String productName, Integer productQuantity, Integer productUnitPrice, Integer productTotalPrice) {
            return new ReservationProductResponse(productImageUrl, productName, productQuantity, productUnitPrice, productTotalPrice);
        }
    }

    public record ReservationSummaryResponse(
            Integer reservationTotalProducts,
            Integer reservationTotalPrice
    ) {
        public static ReservationSummaryResponse of(Integer reservationTotalProducts, Integer reservationTotalPrice) {
            return new ReservationSummaryResponse(reservationTotalProducts, reservationTotalPrice);
        }
    }
}