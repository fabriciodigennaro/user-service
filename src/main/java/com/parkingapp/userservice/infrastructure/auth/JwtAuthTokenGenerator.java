package com.parkingapp.userservice.infrastructure.auth;

import com.parkingapp.userservice.domain.auth.AuthTokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtAuthTokenGenerator implements AuthTokenGenerator {

    @Value("${security.jwt.secret}") private String secretKey;
    @Value("${security.jwt.expiration}")private long tokenExpirationMs;
    @Value("${security.jwt.refresh-token.expiration}") private long refreshExpirationMs;

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tokenExpirationMs))
            .signWith(key())
            .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
            .signWith(key())
            .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
        public String extractUsername(String token) {
        return Jwts.parser()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }
}
