package com.breaditnow.reservation.application.dto.response;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.model.ReservedBakery;

import java.util.List;

public record MyReservationDetailResponse(
        BakeryInfo bakeryInfo,
        ReservationInfo reservationInfo,
        List<ReservationItem> reservationItems,
        ReservationSummary reservationSummary
) {
    public static MyReservationDetailResponse from(Reservation reservation) {
        List<ReservationItem> items = reservation.getReservationProducts().stream()
                .map(ReservationItem::from)
                .toList();

        return new MyReservationDetailResponse(
                BakeryInfo.from(reservation.getReservedBakery()),
                ReservationInfo.from(reservation),
                items,
                ReservationSummary.from(reservation)
        );
    }

    public record BakeryInfo(
            Long bakeryId,
            String bakeryName,
            String bakeryAddress,
            String bakeryPhone,
            String bakeryProfileImageUrl
    ) {
        public static BakeryInfo from(ReservedBakery reservedBakery) {
            return new BakeryInfo(
                    reservedBakery.bakeryId(),
                    reservedBakery.name(),
                    reservedBakery.address(),
                    reservedBakery.phone(),
                    reservedBakery.profileImageUrl()
            );
        }
    }

    public record ReservationInfo(
            Long reservationId,
            Long reservationNumber,
            String reservationTime,
            ReservationStatus reservationStatus
    ) {
        public static ReservationInfo from(Reservation reservation) {
            return new ReservationInfo(
                    reservation.getReservationId(),
                    reservation.getReservationNumber(),
                    reservation.getReservationDate(),
                    reservation.getReservationState().getReservationStatus()
            );
        }
    }

    public record ReservationItem(
            Long productId,
            String productName,
            int productQuantity,
            int productTotalPrice,
            String productImageUrl
    ) {
        public static ReservationItem from(ReservationProduct product) {
            return new ReservationItem(
                    product.getProductId(),
                    product.getProductName(),
                    product.getQuantity(),
                    product.getTotalPrice().getAmount(),
                    product.getProductImage()
            );
        }
    }

    public record ReservationSummary(
            int reservationTotalItems,
            int reservationTotalPrice
    ) {
        public static ReservationSummary from(Reservation reservation) {
            return new ReservationSummary(
                    reservation.getReservationProducts().size(),
                    reservation.getTotalPrice().getAmount()
            );
        }
    }
}
