package com.institute.payment.jwt;

import com.institute.payment.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {

    private String secretKey;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private static final long EXPIRATION_TIME = 1000L * 60 * 60; // 1 hour

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user) {
       Map<String,Object> claims  =new HashMap<>();
        claims.put("name",user.getUsername());
        claims.put("email",user.getEmail());
        claims.put("role",user.getRole().name());
        claims.put("aud","training-institute-users");
        claims.put("iss","auth-service");
        claims.put("nonce", UUID.randomUUID().toString());
        claims.put("sid", UUID.randomUUID().toString());
        claims.put("tid", "tenant-001");
        claims.put("ver", "1.0");
        claims.put("preferred_username", user.getUsername());

        Date now =new Date();
        Date expiryDate=new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .add("alg","HS256")
                .add("kid","training-institute-key")
                .and()
                .claims(claims)
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
    public String extractEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String extractUsername(String token) {
        return getClaims(token).get("preferred_username", String.class);
    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

}
