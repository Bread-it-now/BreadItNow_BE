package com.breaditnow.owner.domain.bakery.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BakeryCreateRequest(
	@NotBlank(message = "이름은 필수 입력 사항입니다.")
	@Schema(description = "빵집 이름", example = "소금 한 꼬집")
	String name,

	@NotBlank(message = "법정행정동 코드는 필수 입력 사항입니다.")
	@Schema(description = "빵집 법정 행정동 코드", example = "1168010100")
	String addressCode,

	@NotBlank(message = "주소는 필수 입력 사항입니다.")
	@Schema(description = "빵집 상세 주소", example = "서울특별시 강남구 역삼동 726")
	String address,

	@NotBlank(message = "전화번호는 필수 입력 사항입니다.")
	@Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
	@Schema(description = "빵집 전화번호", example = "010-1234-5678")
	String phone,

	@Schema(description = "빵집 영업시간", example = "오전 07:00 ~ 오후 10:00")
	String openTime,

	@Schema(description = "빵집 소개글", example = "신선한 재료로 매일 구워내는 따뜻한 빵과 달콤한 디저트가 가득한 소금 한 꼬집입니다!")
	String introduction
) {
}
