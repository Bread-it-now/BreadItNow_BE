package com.breaditnow.auth.adatper.out.jwt;

import ch.qos.logback.core.util.StringUtil;
import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.port.out.AccountRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.common.exception.AuthErrorCode.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Component
public class JwtTokenValidator {
	private final String SECRET_KEY;
	private final AccountRepository accountRepository;

	@Autowired
	public JwtTokenValidator(@Value("${auth.token.secret-key}") String secretKey, AccountRepository accountRepository) {
		this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.accountRepository = accountRepository;
    }

	public String resolveToken(HttpServletRequest request) {
		String authHeader = request.getHeader(AUTHORIZATION);

		final String bearerPrefix = BEARER.getValue() + " ";
		if (authHeader != null && authHeader.startsWith(bearerPrefix)) {
			return authHeader.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new JwtException(TOKEN_INVALID.getMessage());
		} catch (ExpiredJwtException e) {
			throw new JwtException(TOKEN_EXPIRED.getMessage());
		} catch (UnsupportedJwtException e) {
			throw new JwtException(TOKEN_UNSUPPORTED.getMessage());
		} catch (IllegalArgumentException e) {
			throw new JwtException(TOKEN_WRONG.getMessage());
		}
	}

	public Authentication getAuthentication(String token) throws JwtException {
		if (StringUtil.isNullOrEmpty(token))
			return null;

		Claims claims = parseClaims(token);

		Long userId = claims.get("userId", Long.class);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		Account account = accountRepository.findById(userId)
				.orElseThrow(() -> new JwtException("Account not found"));

		AccountContext principal = new AccountContext(account, null);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	private Claims parseClaims(String token) throws JwtException {
		return Jwts
			.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		List<String> authoritiesClaim = (List<String>)claims.get("authorities");
		return authoritiesClaim.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}
}
