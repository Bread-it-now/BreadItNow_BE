package com.breaditnow.customer.domain.reservation.controller.req;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
	@Schema(description = "예약할 빵집의 ID", example = "101")
	@NotNull Long bakeryId,

	@NotNull
	@Schema(
		description = "예약할 상품 목록. 각 항목은 예약할 상품의 상세 정보를 포함합니다.",
		example = "[{ \"productId\": 201, \"quantity\": 2 }]"
	)
	List<ReservationProductRequest> reservationProducts
) {
}
