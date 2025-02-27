package com.breaditnow.common.security.jwt.provider;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.breaditnow.common.security.AccountContext;
import com.breaditnow.common.security.jwt.token.AuthToken;
import com.breaditnow.common.security.jwt.token.AuthTokenType;

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

		Date now = new Date();
		Long expiration =
			tokenType == AuthTokenType.ACCESS ? ACCESS_TOKEN_VALID_MILLISECOND : REFRESH_TOKEN_VALID_MILLI_SECOND;
		Date expiryDate = new Date(now.getTime() + expiration);
		String jwtToken = generateJwt(accountContext, now, expiryDate);

		return AuthToken.builder()
			.userId(accountContext.getUserId())
			.token(jwtToken)
			.expiresIn(expiration)
			.expiryDate(getLocalDateTime(expiryDate))
			.type(tokenType)
			.build();
	}

	private static String getLocalDateTime(Date expiryDate) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	private String generateJwt(AccountContext accountContext, Date now, Date expiryDate) {
		List<String> authorities = accountContext.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setSubject("AuthToken")
			.claim("userId", accountContext.getUserId())
			.claim("authorities", authorities)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

}
