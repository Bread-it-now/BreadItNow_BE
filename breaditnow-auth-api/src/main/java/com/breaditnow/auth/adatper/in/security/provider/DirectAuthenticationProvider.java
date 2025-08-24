package com.breaditnow.auth.adatper.in.security.provider;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.adatper.in.security.service.PrincipalDetailsService;
import com.breaditnow.auth.adatper.in.security.token.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class DirectAuthenticationProvider implements AuthenticationProvider {
    private final PrincipalDetailsService principalDetailsService;
    private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;

        String email = (String) jwtToken.getPrincipal();
        String password = (String) jwtToken.getCredentials();

        AccountContext accountContext = (AccountContext) principalDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new JwtAuthenticationToken(accountContext, null, accountContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
