package com.amigos.customer.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static final String SECRET_KEY = "foobar_1234567890";

    public String issueToken(
            String subject,
            Map<String, Object> claims
    ) {
        Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuer("https://api.com")
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        Date.from(
                                Instant.now().plus(15, ChronoUnit.DAYS)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.ES256);

        return "123";
    }


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
