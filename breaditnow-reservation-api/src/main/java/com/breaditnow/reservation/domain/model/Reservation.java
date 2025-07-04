package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.dto.StockUpdateItem;
import com.breaditnow.common.exception.ReservationErrorCode;
import com.breaditnow.common.exception.ReservationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.breaditnow.common.domain.DailyTime.DATE_FORMATTER;

@Getter
public class Reservation {
    private Long reservationId;
    private ReservedBakery reservedBakery;
    private Orderer orderer;
    private Long reservationNumber;
    private List<ReservationProduct> reservationProducts;
    private ReservationState reservationState;
    private Money totalPrice;
    private LocalDateTime reservationTime;

    @Builder
    private Reservation(Long reservationId, Long reservationNumber, List<ReservationProduct> reservationProducts, ReservedBakery reservedBakery, Orderer orderer, ReservationState reservationState, Money totalPrice,  LocalDateTime reservationTime) {
        this.reservationId = reservationId;
        this.reservedBakery = reservedBakery;
        this.orderer = orderer;
        this.reservationNumber = reservationNumber;
        this.reservationProducts = reservationProducts;
        this.reservationState = reservationState;
        this.totalPrice = totalPrice;
        this.reservationTime = reservationTime;
    }

    public Reservation(Orderer orderer, ReservedBakery reservedBakery, List<ReservationProduct> reservationProducts) {
        this.orderer = orderer;
        this.reservedBakery = reservedBakery;
        this.reservationProducts = reservationProducts;
        this.reservationState = ReservationState.waiting();
        this.totalPrice = calculateTotalPrice();
    }

    public void approve(Long newReservationNumber){
        this.reservationState.approve();
        this.reservationNumber = newReservationNumber;
    }

    public void cancel(String reason) {
        this.reservationState.cancel(reason);
    }

    public void partialApprove(List<StockUpdateItem> updateItems, Long newReservationNumber) {
        this.reservationState.partiallyApprove();

        Map<Long, ReservationProduct> originalProductMap = this.reservationProducts.stream()
                .collect(Collectors.toMap(ReservationProduct::getProductId, product -> product));

        this.reservationProducts = updateItems.stream()
                .map(item -> {
                    ReservationProduct originalProduct = originalProductMap.get(item.productId());
                    if (originalProduct == null) throw new ReservationException(ReservationErrorCode.PRODUCT_NOT_IN_RESERVATION);
                    return new ReservationProduct(
                            originalProduct.getProductId(),
                            originalProduct.getProductName(),
                            originalProduct.getProductImage(),
                            originalProduct.getPrice(),
                            item.quantity()
                    );
                })
                .toList();
        this.reservationNumber = newReservationNumber;
        this.totalPrice = calculateTotalPrice();
    }

    public String getPickupDeadline() {
        LocalDateTime localDateTime = calculatePickupDeadline();
        return localDateTime == null ? null : localDateTime.format(DATE_FORMATTER);
    }

    public String getReservationDate() {
        return this.reservationTime == null ? null : this.reservationTime.format(DATE_FORMATTER);
    }

    public LocalDateTime calculatePickupDeadline() {
        if(this.reservationTime == null || !this.reservationState.isCompleted()) {
            return null;
        }
        return this.reservationTime.plusMinutes(30);
    }

    private Money calculateTotalPrice() {
        return this.reservationProducts.stream()
                .map(ReservationProduct::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }
}
