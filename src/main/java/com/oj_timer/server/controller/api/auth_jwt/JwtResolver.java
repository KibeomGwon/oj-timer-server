package com.oj_timer.server.controller.api.auth_jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtResolver {
    private final String key;

    public JwtResolver(@Value("${JWT.SECRETKEY}") String secretKey) {
        key = JwtConst.getSecretKey(secretKey);
    }

    public boolean validation(String token) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.info("JWT VALIDATE SUCCESS");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token [{}]", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token [{}]", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token [{}]", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty. [{}]", e.getMessage());
        } catch (NullPointerException e) {
            log.info("NOT EXISTS TOKEN [{}]", e.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.info("Signature Exception [{}]", e.getMessage());
        }
        return false;
    }

    public String parseJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.info("JWT PARSE COMPLETE [{}]", claims.getSubject());
        return claims.getSubject();
    }
}