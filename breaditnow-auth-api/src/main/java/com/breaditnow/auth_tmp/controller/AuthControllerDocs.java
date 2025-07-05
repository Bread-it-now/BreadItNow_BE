//package com.breaditnow.auth.controller;
//
//import java.util.Map;
//
//import com.breaditnow.auth.controller.req.SignupRequest;
//import com.breaditnow.common.response.ApiSuccessResponse;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Tag(name = "Auth API")
//public interface AuthControllerDocs {
//	@Operation(
//		summary = "회원 가입",
//		description = "회원 가입 요청을 처리하고, 신규 회원의 식별 정보를 반환합니다."
//	)
//	ApiSuccessResponse<Map<String, Long>> signup(SignupRequest signupRequest);
//
//	@Operation(
//		summary = "로그아웃",
//		description = "현재 사용자의 로그아웃을 처리합니다."
//	)
//	ApiSuccessResponse<Map<String, Long>> logout(HttpServletRequest request, HttpServletResponse response);
//}
