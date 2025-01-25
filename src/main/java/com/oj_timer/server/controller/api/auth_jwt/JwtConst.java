package com.oj_timer.server.controller.api.auth_jwt;

import java.util.Base64;
import java.util.UUID;

public class JwtConst {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 10 * 60 * 1000;
    public static final long REFRESH_TOKEN_EXPIRE_TIME =  30 * 60 * 1000;


    private static final String uuid = UUID.randomUUID().toString();

    // 이 메소드는 나중에 서버에 올릴 때 삭제
    // secretKey는 정적이어야 한다. (UUID가 사용되기 때문에 정적 X)
    public static String getSecretKey(String key) {
        key += uuid;
        return Base64.getEncoder().encodeToString(key.getBytes());
    }
}
