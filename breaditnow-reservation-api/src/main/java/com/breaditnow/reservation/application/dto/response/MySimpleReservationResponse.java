package com.breaditnow.reservation.application.dto.response;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;

import java.util.List;

public record MySimpleReservationResponse(
    ReservationInfo reservationInfo,
    List<ReservationProductResponse> reservationProducts,
    ReservationSummary reservationSummary
) {
    public static MySimpleReservationResponse from(Reservation reservation, BakeryInfo bakeryInfo) {
        ReservationInfo reservationInfo = new ReservationInfo(
                bakeryInfo.name(),
                reservation.getReservationTime().format(DailyTime.DATE_FORMATTER),
                reservation.getReservationState().getReservationStatus()
        );

        List<ReservationProductResponse> products = reservation.getReservationProducts().stream()
                .map(ReservationProductResponse::from)
                .toList();

        ReservationSummary reservationSummary = new ReservationSummary(
                products.size(),
                reservation.getTotalPrice().getAmount()
        );

        return new MySimpleReservationResponse(reservationInfo, products, reservationSummary);
    }

    public record ReservationInfo(
            String bakeryName,
            String reservationDate,
            ReservationStatus reservationStatus
    ) {}

    public record ReservationProductResponse(
            String productImageUrl,
            String productName,
            int productQuantity,
            int productUnitPrice,
            int productTotalPrice
    ) {
        public static ReservationProductResponse from(ReservationProduct product) {
            return new ReservationProductResponse(
                    product.getProductImage(),
                    product.getProductName(),
                    product.getQuantity(),
                    product.getPrice().getAmount(),
                    product.getTotalPrice().getAmount()
            );
        }
    }

    public record ReservationSummary(
            int reservationTotalProducts,
            int reservationTotalPrice
    ) {}
}
