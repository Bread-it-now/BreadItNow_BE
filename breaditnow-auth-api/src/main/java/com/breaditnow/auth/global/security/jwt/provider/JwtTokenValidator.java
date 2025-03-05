package com.breaditnow.auth.global.security.jwt.provider;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.breaditnow.auth.global.security.AccountContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenValidator {

	private final String SECRET_KEY;

	@Autowired
	public JwtTokenValidator(@Value("${auth.token.secret-key}") String secretKey) {
		this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String resolveToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	public Authentication getAuthentication(String token) throws JwtException {
		Claims claims = parseClaims(token);

		Long userId = claims.get("userId", Long.class);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		AccountContext principal = AccountContext.of(userId, authorities);

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
}
