package com.zephyr.base.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;

public class JWTUtils {
    // token 过期时间24小时
    private static int EXPIRETIME = 60 * 24;
    // token secret
    private static final String SIGN = "JIAH@31)SJA";
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

    public static boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public static String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public static String handleReq(HttpServletRequest request) {
        // 读取token
        String authHeader = request.getHeader(AUTHORIZATION);
        // 判断token格式
        if (authHeader != null && authHeader.startsWith(TYPE)) {
            // 得到真正token
            return authHeader.substring(TYPE.length()).trim();
        }
        return null;
    }
}