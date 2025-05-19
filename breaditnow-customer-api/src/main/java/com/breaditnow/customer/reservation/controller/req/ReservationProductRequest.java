package com.breaditnow.customer.reservation.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReservationProductRequest(
	@NotNull
	@Schema(description = "예약할 상품의 ID", example = "201")
	Long productId,
	@NotNull
	@Schema(description = "예약할 상품의 수량", example = "2")
	Integer quantity
) {
}
