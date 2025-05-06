package com.breaditnow.owner.domain.owner.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OwnerPasswordUpdateRequest(
	@NotBlank(message = "새로운 비밀번호 값은 필수 입력 사항입니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,30}$",
		message = "비밀번호는 8자리 이상, 30자리 이하이며, 문자·숫자·특수문자를 각각 1개 이상 포함해야 합니다."
	)
	@Schema(
		example = "NewPassword123!",
		description = "영문·숫자·특수문자를 포함한 8~30자리 비밀번호"
	)
	String newPassword
) {
}
