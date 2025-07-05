package com.breaditnow.auth.adatper.in.security.handler;

import com.breaditnow.common.exception.AuthErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@Component
public class DirectAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		String errorMessage = exception.getMessage();
		AuthErrorCode authErrorCode = AuthErrorCode.valueOf(errorMessage);

		log.error("[{}] code={}, message={}", exception.getClass().getName(), authErrorCode.getCode(),
			exception.getMessage());

		response.setContentType(APPLICATION_JSON_UTF8_VALUE);
		String responseBody = new ObjectMapper().writeValueAsString(ApiErrorResponse.of(authErrorCode));
		response.getWriter().write(responseBody);
	}
}
