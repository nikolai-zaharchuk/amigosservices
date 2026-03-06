package com.amigos.shoppingcart.service;

import com.amigos.shoppingcart.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        final long tokenExpiration = System.currentTimeMillis() + 1000 * 60 * 60 * 10;

        return Jwts.builder()
                .subject(user.getId().toString())
                .claims(Map.of("email", user.getEmail(), "name", user.getName()))
                .issuedAt(new Date())
                .expiration(new Date(tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getExpiration().after(new Date());

        } catch (JwtException exception) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = parseToken(token);

        return claims.getSubject();
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
