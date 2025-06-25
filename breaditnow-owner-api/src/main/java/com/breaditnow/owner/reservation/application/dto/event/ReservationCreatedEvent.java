package com.breaditnow.owner.reservation.application.dto.event;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationCreatedEvent(
        Long reservationId,
        Long bakeryId,
        Long customerId,
        LocalDateTime reservationTime,
        String status,
        MoneyInfo totalPrice,
        List<ProductRequestInfo> products
) {
    public record MoneyInfo(Integer amount) {}
    public record ProductRequestInfo(Long productId, Integer quantity) {}
}