package com.breaditnow.auth.global.security.direct.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.breaditnow.auth.global.exception.AuthErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DirectAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {

		String errorMessage = exception.getMessage();

		AuthErrorCode authErrorCode = AuthErrorCode.valueOf(errorMessage);

		response.setContentType("application/json;charset=UTF-8");
		String responseBody = new ObjectMapper().writeValueAsString(
			ApiErrorResponse.of(authErrorCode));
		response.getWriter().write(responseBody);
	}
}
