package com.breaditnow.owner.domain.reservation.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationItemUpdateRequest(
	@NotNull
	@Schema(description = "예약 아이템에 해당하는 상품 ID", example = "101")
	Long productId,
	@Min(value = 1, message = "수량은 1 이상이어야 합니다.")
	@Schema(description = "예약할 상품 수량", example = "2")
	int quantity
) {
}
