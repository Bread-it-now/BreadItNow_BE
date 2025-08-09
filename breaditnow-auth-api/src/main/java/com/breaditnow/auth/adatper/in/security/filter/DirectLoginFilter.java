package com.breaditnow.auth.adatper.in.security.filter;

import com.breaditnow.auth.adatper.in.security.token.JwtAuthenticationToken;
import com.breaditnow.auth.application.dto.request.DirectLoginRequest;
import com.breaditnow.common.exception.AuthException;
import com.breaditnow.common.util.JsonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.breaditnow.common.exception.AuthErrorCode.EMAIL_NOT_FOUND;

@RequiredArgsConstructor
public class DirectLoginFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        DirectLoginRequest loginRequest = JsonUtil.readValue(request, DirectLoginRequest.class);

        if (!StringUtils.hasText(loginRequest.email()) || !StringUtils.hasText(loginRequest.password())) {
            throw new AuthException(EMAIL_NOT_FOUND);
        }

        JwtAuthenticationToken token = new JwtAuthenticationToken(
                loginRequest.email(),
                loginRequest.password()
        );

		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authentication);
	}
}
