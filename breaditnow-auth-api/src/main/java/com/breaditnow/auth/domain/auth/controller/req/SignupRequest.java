package com.breaditnow.auth.domain.auth.controller.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
	@NotBlank(message = "이메일은 필수 항목입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	String email,

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$", message = "비밀번호는 8자리 이상, 30자리 이하이며, 문자, 숫자, 특수문자를 포함해야 합니다.")
	String password,

	@NotBlank(message = "역할은 필수 항목입니다.")
	String role
) {
}
