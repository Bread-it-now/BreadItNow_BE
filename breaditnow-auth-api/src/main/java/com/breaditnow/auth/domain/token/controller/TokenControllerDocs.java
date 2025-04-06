package com.breaditnow.auth.domain.token.controller;

import com.breaditnow.common.response.ApiSuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Token API")
public interface TokenControllerDocs {
	@Operation(
		summary = "액세스 토큰 재발급",
		description = "HTTP 요청과 응답 객체를 사용하여 액세스 토큰을 재발급합니다."
	)
	ApiSuccessResponse<Void> reissueAccessToken(HttpServletResponse response, HttpServletRequest request);

}
