package com.manhhoach.EofficeFull.provider;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private SecretKey getSecretKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject, Map<String, Object> claims, String key, long expiration) {
        SecretKey secretKey = getSecretKey(key);
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String key) {
        try {
            parseClaims(token, key);
            return true;
        } catch (ExpiredJwtException e) {
            // Token hết hạn
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            // Sai signature, format sai, hoặc null
            return false;
        }
    }

    public Claims parseClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getUsername(String token, String key) {
        try {
            return parseClaims(token, key).getSubject();
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }
    }

    public List<String> getPermissions(String token, String key) {
        try {
            return parseClaims(token, key).get("permissions", List.class);
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }
    }

    public Long getId(String token, String key) {
        try {
            return parseClaims(token, key).get("id", Long.class);
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }
    }
}
