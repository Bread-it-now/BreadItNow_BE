package com.breaditnow.auth.adatper.in.security.handler;

import com.breaditnow.auth.adatper.in.security.oauth2.CookieOAuth2AuthorizationRequestRepository;
import com.breaditnow.common.exception.AuthErrorCode;
import com.breaditnow.common.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.breaditnow.auth.adatper.in.security.oauth2.CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final CookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;
	private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = cookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue)
			.orElse(("/"));

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("error", exception.getLocalizedMessage())
			.build().encode().toUriString();

		String errorMessage = exception.getMessage();
		try {
			AuthErrorCode errorCode = AuthErrorCode.valueOf(errorMessage);
			log.error("[{}] code={}, message={}",
				exception.getClass().getName(),
				errorCode.getCode(),
				errorCode.getMessage());
		} catch (Exception e) {
			log.error("[{}] {}", exception.getClass().getName(), errorMessage);
		}

		cookieAuthorizationRequestRepository.removeAuthorizationRequest(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
