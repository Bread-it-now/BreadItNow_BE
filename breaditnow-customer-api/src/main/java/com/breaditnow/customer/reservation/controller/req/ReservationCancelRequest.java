package com.breaditnow.customer.reservation.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ReservationCancelRequest(
	@NotBlank
	@Schema(description = "예약을 취소하는 사유", example = "개인 사정으로 예약을 취소합니다.")
	String reason
) {

}
