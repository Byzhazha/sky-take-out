package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成JWT (此方法在 0.12.5 版本中仍然有效)
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 将密钥字符串转换为安全密钥对象
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 生成JWT的过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                // 设置签名使用的密钥。 signWith(Key) 在 0.12.5 中仍然是推荐用法
                .signWith(key)
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token解密 (针对 0.12.5 版本修正)
     *
     * @param secretKey jwt秘钥
     * @param token     加密后的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 将密钥字符串转换为安全密钥对象
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 解析token
        Claims claims = Jwts.parser()
                // 设置用于验证签名的密钥
                .verifyWith(key)
                .build()
                // 解析token字符串
                .parseSignedClaims(token)
                // 获取Claims部分
                .getPayload();

        return claims;
    }
}