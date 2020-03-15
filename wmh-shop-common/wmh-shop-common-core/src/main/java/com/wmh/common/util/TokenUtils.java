package com.wmh.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtils {
    @Autowired
    private RedisUtil redisUtil;

    public String createToken(String value) {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisUtil.setString(token, value);
        return token;
    }

    public String getTokenValue(String token) {
        return redisUtil.getString(token);
    }

    public void delToken(String token) {
        redisUtil.delKey(token);
    }

    public String createToken(String prefix, String value, Long timeout) {
        String token = prefix + "_" + UUID.randomUUID().toString().replace("-", "");
        redisUtil.setString(token, value, timeout);
        return token;
    }
}
