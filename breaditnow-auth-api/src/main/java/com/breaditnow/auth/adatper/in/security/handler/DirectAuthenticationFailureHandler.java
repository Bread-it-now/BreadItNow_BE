package com.breaditnow.auth.adatper.in.security.handler;

import com.breaditnow.common.exception.AuthErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		AuthErrorCode errorCode;
		if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
			errorCode = AuthErrorCode.INVALID_CREDENTIALS;
		} else if (exception instanceof CredentialsExpiredException) {
			errorCode = AuthErrorCode.TOKEN_EXPIRED;
		} else {
			errorCode = AuthErrorCode.AUTHENTICATION_FAILED;
		}

        response.setStatus(errorCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		String responseBody = objectMapper.writeValueAsString(ApiErrorResponse.of(errorCode));
		response.getWriter().write(responseBody);
	}
}
