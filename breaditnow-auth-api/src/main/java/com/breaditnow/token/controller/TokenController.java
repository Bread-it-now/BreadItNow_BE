package com.breaditnow.token.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.token.service.TokenService;
import com.breaditnow.common.response.ApiSuccessResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController implements TokenControllerDocs {

	private final TokenService tokenService;

	@PostMapping("/refresh")
	public ApiSuccessResponse<Void> reissueAccessToken(HttpServletResponse response, HttpServletRequest request) {
		tokenService.reissueToken(request, response);
		return ApiSuccessResponse.of();
	}
}
