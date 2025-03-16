package com.breaditnow.owner.domain.bakery.controller.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BakeryCreateRequest(
	@NotBlank(message = "이름은 필수 입력 사항입니다.")
	String name,

	@NotBlank(message = "법정행정동 코드는 필수 입력 사항입니다.")
	String addressCode,

	@NotBlank(message = "주소는 필수 입력 사항입니다.")
	String address,

	@NotBlank(message = "전화번호는 필수 입력 사항입니다.")
	@Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
	String phone,

	String openTime,
	String introduction
) {
}
