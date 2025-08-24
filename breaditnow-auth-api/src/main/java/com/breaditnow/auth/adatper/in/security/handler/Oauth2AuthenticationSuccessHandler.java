package com.breaditnow.auth.adatper.in.security.handler;

import com.breaditnow.auth.adatper.in.security.oauth2.CookieOAuth2AuthorizationRequestRepository;
import com.breaditnow.auth.adatper.out.jwt.JwtTokenCreator;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
import com.breaditnow.common.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.breaditnow.auth.adatper.in.security.oauth2.CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType.REFRESH;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenCreator jwtTokenCreator;
    private final CookieUtil cookieUtil;
    private final CookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final AuthTokenRepository authTokenRepository;

    @Value("${auth.token.refresh-cookie-key}")
    private String refreshCookieKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            return;
        }

        AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
        authTokenRepository.saveRefreshToken(refreshToken);

        int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
        cookieUtil.addHttpOnlyCookie(response, refreshCookieKey, refreshToken.token(), maxAge);

        String finalUrl = UriComponentsBuilder.fromUriString(targetUrl).build().toUriString();

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, finalUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl = cookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(getDefaultTargetUrl());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
    }
}
