package com.oj_timer.server.controller.api.auth_jwt;

import com.oj_timer.server.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.oj_timer.server.controller.api.auth_jwt.JwtConst.*;

@Component
@Slf4j
public class JwtGenerator {
    private final String key;
    private final RefreshTokenRepository repository;

    public JwtGenerator(@Value("${JWT.SECRETKEY}") String secretKey, RefreshTokenRepository repository) throws Exception {
        key = getSecretKey(secretKey);
        this.repository = repository;
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

        repository.save(memberEmail, refreshToken);

        log.info("REFRESH TOKEN [{}], STORED REFRESH TOKEN [{}]", refreshToken, repository.findOne(memberEmail));

        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
