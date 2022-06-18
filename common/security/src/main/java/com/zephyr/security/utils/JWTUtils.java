package com.zephyr.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;

public class JWTUtils {
    // token 过期时间24小时
    private static int EXPIRETIME = 60 * 24;
    // token secret
    private static String SIGN = "JIAH@31)SJA";
    public static String TYPE = "Bearer";
    public static String AUTHORIZATION = "Authorization";

    public static HashMap<String, Object> createToken(String userName) {
        HashMap<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRETIME);
        String token = JWT.create().withSubject(userName).withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SIGN));
        map.put("access_token", token);
        map.put("expires_in", EXPIRETIME);
        map.put("refresh_token", token);
        map.put("token_type", TYPE);
        return map;
    }

    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    public static String getUserName(String token) {
        return JWT.decode(token).getAudience().get(0);
    }
}