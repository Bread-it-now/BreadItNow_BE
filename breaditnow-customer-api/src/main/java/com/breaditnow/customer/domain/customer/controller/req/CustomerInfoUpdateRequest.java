package com.breaditnow.customer.domain.customer.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerInfoUpdateRequest(
	@NotBlank(message = "이름은 필수 입력 사항입니다.")
	@Schema(description = "고객 이름", example = "홍길동", required = true)
	String nickname,

	@NotBlank(message = "전화번호는 필수 입력 사항입니다.")
	@Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
	@Schema(description = "고객 휴대폰 번호", example = "010-1234-5678", required = true)
	String phone,

	@Nullable
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,30}$",
		message = "비밀번호는 8자리 이상, 30자리 이하이며, 문자·숫자·특수문자를 각각 1개 이상 포함해야 합니다."
	)
	@Schema(
		example = "NewPassword123!",
		description = "영문·숫자·특수문자를 포함한 8~30자리 비밀번호",
		required = false
	)
	String newPassword
) {
}
