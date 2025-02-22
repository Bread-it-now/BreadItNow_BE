package com.breaditnow.auth.global.security.jwt;

import static com.breaditnow.auth.domain.token.domain.AuthTokenType.*;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.breaditnow.auth.domain.token.domain.AuthToken;
import com.breaditnow.auth.domain.token.domain.AuthTokenType;
import com.breaditnow.auth.global.security.AccountContext;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenCreator {
	private final Long ACCESS_TOKEN_VALID_MILLISECOND;
	private final Long REFRESH_TOKEN_VALID_MILLI_SECOND;
	private final String SECRET_KEY;

	private final Key key;

	@Autowired
	public JwtTokenCreator(
		@Value("${auth.token.secret-key}") String secretKey,
		@Value("${auth.token.access-token-valid-millisecond}") Long accessTokenValidMillisecond,
		@Value("${auth.token.refresh-token-valid-millisecond}") Long refreshTokenValidMillisecond) {
		this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
		this.ACCESS_TOKEN_VALID_MILLISECOND = accessTokenValidMillisecond;
		this.REFRESH_TOKEN_VALID_MILLI_SECOND = refreshTokenValidMillisecond;
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}

	public AuthToken createToken(Authentication authentication, AuthTokenType tokenType) {
		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
		Long expiration = tokenType == ACCESS ? ACCESS_TOKEN_VALID_MILLISECOND : REFRESH_TOKEN_VALID_MILLI_SECOND;

		String jwtToken = generateJwt(accountContext, expiration);

		return AuthToken.builder()
			.userId(accountContext.getUserId())
			.token(jwtToken)
			.expiresIn(expiration)
			.type(tokenType)
			.build();
	}

	private String generateJwt(AccountContext accountContext, Long expiration) {
		List<String> authorities = accountContext.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setSubject("AuthToken")
			.claim("id", accountContext.getUserId())
			.claim("authorities", authorities)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

}
