package com.breaditnow.customer.reservation.presentation.response;

import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryEntity;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationDetailResponse {
    private SimpleBakeryResponse bakery;
    private ReservationResponse reservations;

    public static ReservationDetailResponse from(BakeryEntity bakeryEntity, ReservationEntity reservationEntity) {
        SimpleBakeryResponse bakeryResponse = SimpleBakeryResponse.of(bakeryEntity);
        ReservationResponse reservationResponse = ReservationResponse.of(reservationEntity);
        return new ReservationDetailResponse(bakeryResponse, reservationResponse);
    }

    public record SimpleBakeryResponse(
            Long bakeryId,
            String bakeryName,
            String bakeryAddress,
            String bakeryPhone,
            String bakeryProfileImage
    ) {
        public static SimpleBakeryResponse of(BakeryEntity bakeryEntity) {
            return new SimpleBakeryResponse(
                    bakeryEntity.getId(),
                    bakeryEntity.getName(),
                    bakeryEntity.getAddressDescription(),
                    bakeryEntity.getPhone(),
                    bakeryEntity.getProfileImage()
            );
        }
    }

    public record ReservationResponse (
        Long reservationId,
        Long reservationNumber,
        LocalDateTime reservationTime,
        ReservationStatus reservationStatus,
        Integer reservationTotalPrice,
        List<ReservationItemResponse> reservationItems
    ) {
        public static ReservationResponse of(ReservationEntity reservationEntity) {
            return new ReservationResponse(
                    reservationEntity.getId(),
                    reservationEntity.getReservationNumber(),
                    reservationEntity.getReservationTime(),
                    reservationEntity.getReservationStatus(),
                    reservationEntity.getTotalPrice(),
                    reservationEntity.getReservationItems().stream()
                            .map(item -> ReservationItemResponse.of(
                                            item.getProductId(),
                                            item.getProductName(),
                                            item.getQuantity(),
                                            item.getTotalPrice(),
                                            item.getProductImageUrl()
                                    )
                            ).toList()

            );
        }
    }

    public record ReservationItemResponse(
        Long productId,
        String productName,
        Integer productQuantity,
        Integer productTotalPrice,
        String productImageUrl
    ) {
        public static ReservationItemResponse of(Long productId, String productName, Integer quantity, Integer price, String productImage) {
            return new ReservationItemResponse(productId, productName, quantity, price, productImage);
        }
    }
}
