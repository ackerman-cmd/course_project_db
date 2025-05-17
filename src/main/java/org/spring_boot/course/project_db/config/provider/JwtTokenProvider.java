package org.spring_boot.course.project_db.config.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider {

    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final int jwtExpirationMs = 36000; // 1 минута

    @Getter
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();


        redisTemplate.opsForValue().set("token:" + token, username, jwtExpirationMs, TimeUnit.MILLISECONDS);
        // Храним связь username с токеном в хэше для возможности отзыва
        redisTemplate.opsForHash().put("user:tokens:" + username, token, String.valueOf(System.currentTimeMillis()));
        return token;
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        // Проверяем наличие токена в Redis
        if (redisTemplate.hasKey("token:" + token)) {
            return true;
        }
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }


    public void invalidateToken(String token, String username) {
        redisTemplate.delete("token:" + token);
        redisTemplate.opsForHash().delete("user:tokens:" + username, token);
    }
}