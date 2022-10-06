package com.motivation.mojaty.global.provider.jwt;

public class JwtProperties {
    public static final Long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;
    public static final Long REFRESH_TOKEN_VALID_TIME = 30 * 24 * 60 * 60 * 1000L;
    public static final String JWT_HEADER = "ACCESS-TOKEN";
}
