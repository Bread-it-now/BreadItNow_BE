package com.breaditnow.gateway.jwt;

import com.breaditnow.gateway.exception.GatewayException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

import static com.breaditnow.gateway.exception.GatewayErrorCode.*;

@Component
public class JwtTokenValidator {
	private final String SECRET_KEY;

	@Autowired
	public JwtTokenValidator(@Value("${auth.token.secret-key}") String secretKey) {
		this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new GatewayException(TOKEN_INVALID);
		} catch (ExpiredJwtException e) {
			throw new GatewayException(TOKEN_EXPIRED);
		} catch (UnsupportedJwtException e) {
			throw new GatewayException(TOKEN_UNSUPPORTED);
		} catch (IllegalArgumentException e) {
			throw new GatewayException(TOKEN_WRONG);
		}
	}

	public TokenUser getTokenUser(String token) throws JwtException {
		Claims claims = parseClaims(token);

		Long userId = claims.get("userId", Long.class);
		if (userId == null) {
			throw new GatewayException(TOKEN_USER_ID_MISSING);
		}

		List<String> authorities = claims.get("authorities", List.class);
		if (authorities == null || authorities.isEmpty() || authorities.get(0) == null) {
			throw new GatewayException(TOKEN_USER_ROLE_MISSING);
		}

		return new TokenUser(String.valueOf(userId), authorities.get(0));
	}

	private Claims parseClaims(String token) throws JwtException {
		return Jwts
			.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
