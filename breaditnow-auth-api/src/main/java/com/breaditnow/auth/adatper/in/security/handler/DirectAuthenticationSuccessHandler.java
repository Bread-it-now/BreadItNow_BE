package com.breaditnow.auth.adatper.in.security.handler;


import com.breaditnow.auth.adatper.out.jwt.JwtTokenCreator;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType;
import com.breaditnow.common.util.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class DirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final JwtTokenCreator jwtTokenCreator;
	private final CookieUtil cookieUtil;

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthToken accessToken = jwtTokenCreator.createToken(authentication, AuthTokenType.ACCESS);
        AuthToken refreshToken = jwtTokenCreator.createToken(authentication, AuthTokenType.REFRESH);

        response.addHeader(AUTHORIZATION, "Bearer " + accessToken.token());

        int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
        cookieUtil.addCookie(response, refreshCookieKey, refreshToken.token(), maxAge);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 확인용
        objectMapper.writeValue(response.getWriter(), authentication.getPrincipal());
	}
}
