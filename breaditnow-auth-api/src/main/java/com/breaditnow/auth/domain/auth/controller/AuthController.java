package com.breaditnow.auth.domain.auth.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.auth.domain.auth.controller.req.SignupRequest;
import com.breaditnow.auth.domain.auth.service.AuthService;
import com.breaditnow.common.response.ApiSuccessResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
	private final AuthService authService;

	@PostMapping("/sign-up")
	public ApiSuccessResponse<Map<String, Long>> createUser(@RequestBody SignupRequest signupRequest) {
		log.info("signupRequest = {}", signupRequest);
		Long userId = authService.signup(signupRequest);
		return ApiSuccessResponse.of("userId", userId);
	}
}
