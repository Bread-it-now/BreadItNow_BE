//package com.breaditnow.auth.controller.req;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//
//public record SignupRequest(
//	@NotBlank(message = "이메일은 필수 항목입니다.")
//	@Email(message = "이메일 형식이 올바르지 않습니다.")
//	@Schema(example = "user@example.com", description = "가입할 사용자의 이메일 주소")
//	String email,
//
//	@NotBlank(message = "비밀번호는 필수 항목입니다.")
//	@Pattern(
//		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,30}$",
//		message = "비밀번호는 8자리 이상, 30자리 이하이며, 문자·숫자·특수문자를 각각 1개 이상 포함해야 합니다."
//	)
//	@Schema(
//		example = "Password123!",
//		description = "영문·숫자·특수문자를 포함한 8~30자리 비밀번호"
//	)
//	String password,
//
//	@NotBlank(message = "역할은 필수 항목입니다.")
//	@Schema(example = "customer", description = "사용자 역할 (예: customer, owner)")
//	String role
//) {
//}
