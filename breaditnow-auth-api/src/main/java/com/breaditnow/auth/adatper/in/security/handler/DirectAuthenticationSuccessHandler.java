package com.breaditnow.auth.adatper.in.security.handler;


import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.adatper.out.jwt.JwtTokenCreator;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.domain.port.out.SaveAuthTokenPort;
import com.breaditnow.common.response.ApiSuccessResponse;
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
import java.util.Map;

import static com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType.ACCESS;
import static com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType.REFRESH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class DirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenCreator jwtTokenCreator;
	private final CookieUtil cookieUtil;
    private final SaveAuthTokenPort saveAuthTokenPort;
    private final ObjectMapper objectMapper;

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
        AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
        saveAuthTokenPort.saveRefreshToken(refreshToken);

        response.setStatus(HttpStatus.OK.value());
        response.addHeader(AUTHORIZATION, "Bearer " + accessToken.token());

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();

        Map<String, Long> responseData = Map.of("userId", accountContext.getAccount().getId());
        ApiSuccessResponse<Map<String, Long>> apiResponse = ApiSuccessResponse.of(responseData);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
        cookieUtil.addCookie(response, refreshCookieKey, refreshToken.token(), maxAge);

        objectMapper.writeValue(response.getWriter(), apiResponse);
	}
}
