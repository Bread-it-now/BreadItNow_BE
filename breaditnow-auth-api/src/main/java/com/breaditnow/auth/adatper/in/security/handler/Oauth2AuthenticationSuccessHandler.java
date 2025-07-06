package com.breaditnow.auth.adatper.in.security.handler;

import com.breaditnow.auth.adatper.out.jwt.JwtTokenCreator;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.common.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType.ACCESS;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenCreator jwtTokenCreator;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthToken authToken = jwtTokenCreator.createToken(authentication, ACCESS);

        String targetUrl = determineTargetUrl(request, response, authentication);

        String finalUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", authToken.token())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, finalUrl);
    }
}
