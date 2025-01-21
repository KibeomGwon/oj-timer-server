package com.oj_timer.server.controller.api.auth_jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.oj_timer.server.controller.api.auth_jwt.JwtConst.*;

@Component
public class JwtGenerator {
    private final String key;

    public JwtGenerator(@Value("${JWT.SECRETKEY}") String secretKey) {
        key = secretKey;
    }

    public JwtDto generate(String memberEmail) {
        long now = (new Date()).getTime();

        Date accessTokenExpireTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpireTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // accessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(memberEmail)
                .setExpiration(accessTokenExpireTime)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        //
        String refreshToken = Jwts.builder()
                .setSubject(memberEmail)
                .setExpiration(refreshTokenExpireTime)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
