package com.oj_timer.server.controller.api.auth_jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class JwtConst {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 1000;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 60 * 1000;

    private static final String uuid = UUID.randomUUID().toString();

    public static String getSecretKey(String key) {
        key += uuid;
        return Base64.getEncoder().encodeToString(key.getBytes());
    }
}
