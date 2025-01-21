package com.oj_timer.server.controller.api.auth_jwt;

public class JwtConst {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 20 * 60 * 1000;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 3 * 24 * 60 * 60 * 1000;
}
