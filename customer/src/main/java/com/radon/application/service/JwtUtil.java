package com.radon.application.service;

import com.radon.domain.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${security.jwt.secret}") String secretKey){
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String generateAccessToken(UserDetails userDetails){
        return generateToken(userDetails,TokenType.ACCESS);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return generateToken(userDetails,TokenType.REFRESH);
    }

    private String generateToken(UserDetails userDetails,TokenType tokenType){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("type", tokenType.name())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(tokenType.getExpiration())))
                .signWith(secretKey)
                .compact();
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails user){
        Claims claims = parseClaims(token);
        return claims.getSubject().equals(user.getUsername()) && !claims.getExpiration().before(new Date());
    }

    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }

}
