package com.breaditnow.auth.adatper.out.jwt;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtTokenCreator {
    private final Long ACCESS_TOKEN_VALID_MILLISECOND;
    private final Long REFRESH_TOKEN_VALID_MILLI_SECOND;
    private final String SECRET_KEY;
    private final Key key;

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
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();

        Date now = new Date();
        Long expiration = tokenType == AuthTokenType.ACCESS ? ACCESS_TOKEN_VALID_MILLISECOND : REFRESH_TOKEN_VALID_MILLI_SECOND;
        Date expiryDate = new Date(now.getTime() + expiration);

        String jwtToken = generateJwt(accountContext, now, expiryDate);

        return AuthToken.builder()
                .userId(accountContext.getAccount().getId())
                .token(jwtToken)
                .expiresIn(expiration)
                .expiryDate(getLocalDateTime(expiryDate))
                .type(tokenType)
                .build();
    }

    private String generateJwt(AccountContext principal, Date now, Date expiryDate){
        List<String> authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setSubject("AuthToken")
                .claim("userId", principal.getAccount().getId())
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, HS512)
                .compact();
    }

    private String getLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
