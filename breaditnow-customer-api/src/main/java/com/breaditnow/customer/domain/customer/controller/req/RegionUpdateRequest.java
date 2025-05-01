package com.breaditnow.customer.domain.customer.controller.req;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegionUpdateRequest(
	@Schema(description = "시도 코드", example = "11")
	String sidoCode,

	@Schema(description = "구군 코드 목록", example = "[\"110\", \"140\"]")
	List<String> gugunCodes
) {
}
