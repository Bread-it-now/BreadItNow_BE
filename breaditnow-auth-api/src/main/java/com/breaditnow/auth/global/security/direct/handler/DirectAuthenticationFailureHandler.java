package com.breaditnow.auth.global.security.direct.handler;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.breaditnow.auth.global.exception.AuthErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DirectAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		log.info("exception = {}", exception);

		AuthErrorCode authErrorCode;
		if (exception instanceof UsernameNotFoundException) {
			authErrorCode = AuthErrorCode.EMAIL_NOT_FOUND;
		} else if (exception instanceof BadCredentialsException) {
			authErrorCode = AuthErrorCode.INVALID_PASSWORD;
		} else {
			authErrorCode = AuthErrorCode.LOGIN_FAILED;
		}

		response.setContentType("application/json;charset=UTF-8");
		String responseBody = new ObjectMapper().writeValueAsString(
			ApiErrorResponse.of(authErrorCode));
		response.getWriter().write(responseBody);
	}
}
