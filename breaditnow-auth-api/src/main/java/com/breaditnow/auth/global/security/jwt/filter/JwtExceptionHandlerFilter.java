package com.breaditnow.auth.global.security.jwt.filter;

import static com.breaditnow.common.exception.CommonErrorCode.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.breaditnow.common.exception.ErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
	/**
	 * 토큰 관련 에러 핸들링
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			setErrorResponse(request, response, e);
		}
	}

	private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, JwtException ex) {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();

		ErrorCode errorCode = determineErrorCode(ex);
		try {
			response.getWriter().write(
				objectMapper.writeValueAsString(ApiErrorResponse.of(errorCode, ex.getMessage()))
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private ErrorCode determineErrorCode(Exception e) {
		if (e instanceof JwtException) {
			String errorMessage = e.getMessage();
			if (errorMessage.contains(TOKEN_EXPIRED.getMessage())) {
				return TOKEN_EXPIRED;
			} else if (errorMessage.contains(TOKEN_UNSUPPORTED.getMessage())) {
				return TOKEN_UNSUPPORTED;
			}
		}
		return TOKEN_INVALID;
	}
}
