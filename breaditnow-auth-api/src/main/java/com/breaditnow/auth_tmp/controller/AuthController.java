//package com.breaditnow.auth.controller;
//
//import java.util.Map;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.breaditnow.auth.controller.req.SignupRequest;
//import com.breaditnow.auth.service.AuthSignInService;
//import com.breaditnow.auth.service.AuthSignOutService;
//import com.breaditnow.common.response.ApiSuccessResponse;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/auth")
//public class AuthController implements AuthControllerDocs {
//
//	private final AuthSignInService authSignInService;
//	private final AuthSignOutService authSignOutService;
//
//	@PostMapping("/sign-up")
//	public ApiSuccessResponse<Map<String, Long>> signup(@RequestBody @Valid SignupRequest signupRequest) {
//		Long userId = authSignInService.signup(signupRequest);
//		return ApiSuccessResponse.of("userId", userId);
//	}
//
//	@PostMapping("/logout")
//	public ApiSuccessResponse<Map<String, Long>> logout(HttpServletRequest request, HttpServletResponse response) {
//		authSignOutService.logout(request, response);
//		return ApiSuccessResponse.of();
//	}
//}
