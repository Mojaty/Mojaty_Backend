package com.motivation.mojaty.global.provider.jwt;

public class JwtProperties {
    public static final long ACCESS_TOKEN_VALID_TIME = 60 * 3 * 1000L;
    public static final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 30 * 1000L;
    public static final String JWT_HEADER = "ACCESS-TOKEN";
}
