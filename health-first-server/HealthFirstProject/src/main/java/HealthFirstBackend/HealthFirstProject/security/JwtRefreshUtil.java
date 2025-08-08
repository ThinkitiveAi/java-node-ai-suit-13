package HealthFirstBackend.HealthFirstProject.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;

@Component
public class JwtRefreshUtil {
    private final String refreshSecret = "ReplaceWithARefreshSecretKeyForJWTRefreshTokens1234567890";
    private final long refreshExpirationMs = 604800000; // 7 days
    private final Key key = Keys.hmacShaKeyFor(refreshSecret.getBytes());

    public String generateRefreshToken(String providerId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("providerId", providerId);
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
} 