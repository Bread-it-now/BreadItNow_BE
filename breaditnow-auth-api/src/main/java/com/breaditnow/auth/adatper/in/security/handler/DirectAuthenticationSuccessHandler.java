package com.breaditnow.auth.adatper.in.security.handler;


import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.adatper.out.jwt.JwtTokenCreator;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
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
    private final AuthTokenRepository authTokenRepository;
    private final ObjectMapper objectMapper;

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
        AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
        authTokenRepository.saveRefreshToken(refreshToken);

        prepareResponse(response);
        addTokensToResponse(response, accessToken, refreshToken);
        writeSuccessResponse(response, authentication);
	}

    private void prepareResponse(HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }

    private void addTokensToResponse(HttpServletResponse response, AuthToken accessToken, AuthToken refreshToken) {
        response.addHeader(AUTHORIZATION, "Bearer " + accessToken.token());
        int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
        cookieUtil.addHttpOnlyCookie(response, refreshCookieKey, refreshToken.token(), maxAge);
    }

    private void writeSuccessResponse(HttpServletResponse response, Authentication authentication) throws IOException {
        AccountContext principal = (AccountContext) authentication.getPrincipal();
        Map<String, Long> responseData = Map.of("userId", principal.getAccount().getId());
        ApiSuccessResponse<Map<String, Long>> apiResponse = ApiSuccessResponse.of(responseData);
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
