package com.amigos.customer.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "fCcaXeHLgdiGTMDJYBjFmiaUScqhMRQrPA2JXBIi2QUpDHh9cCz8ijKy25a1rqqYbKW7hrB67TGNbU2T25aE6lwhPUbjoLmaoZs3ynglag79EM6DVEoOgpDHuKIcKpgWynpr1JoIcb7ggu9hrBhHGemITCxSoz42qFR2o7Ir2pjQ6RxvPsYi4Z9Uank85nYtmtspuWxydjhKsny7Zmhp83RfBd21osFbuKoZjUmzxbgMlNv1ZIbt1WgHlkHGwRfc";

    public String generateToken(String subject) {
        return generateToken(subject, Map.of());
    }

    public String generateToken(String subject, String ...scopes) {
        return generateToken(subject, Map.of("scopes", scopes));
    }

    public String generateToken(
            String subject,
            Map<String, Object> claims
    ) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuer("https://api.com")
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        Date.from(
                                Instant.now().plus(15, ChronoUnit.DAYS)
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
