package com.sunyt.loginToken.service;

import com.sunyt.loginToken.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {
    @Resource
    private RedisUtil redisUtil;

    public String generateToken(String userAgentStr, String userName){
        StringBuilder token = new StringBuilder("token:");
        //问题
    }
}
