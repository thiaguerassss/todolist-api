package com.thiago.todo_list.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username){
        SecretKey key = getKeyBySecret();
        return Jwts.builder()
                .claim("sub", username)
                .claim("exp", new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    private SecretKey getKeyBySecret(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean isValid(String token){
        Claims claims = getClaims(token);
        if (Objects.nonNull(claims)){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = Date.from(Instant.now());
            return Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate);
        }
        return false;
    }

    private Claims getClaims(String token){
        SecretKey key = getKeyBySecret();
        try {
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getPayload();
        } catch (Exception e){
            return null;
        }
    }
}
