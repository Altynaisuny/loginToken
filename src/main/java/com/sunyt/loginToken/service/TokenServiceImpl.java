package com.sunyt.loginToken.service;

import com.sunyt.loginToken.dto.TokenModel;
import com.sunyt.loginToken.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService{
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public TokenModel createToken(String userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel tokenModel = new TokenModel(userId, token);
        //存储redis并设置过期时间
        redisUtil.setex(userId, token, 3000, TimeUnit.SECONDS);
        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null){
            return false;
        }
        String token = redisUtil.getStr(model.getUserId());
        //验证token是否相同
        if (token == null || !token.equals(model.getToken())){
            return false;
        }
        //验证成功，延长token的过期时间
        redisUtil.expire(model.getUserId(), 3000, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public TokenModel getToken(String authentication) {
        if (StringUtils.isEmpty(authentication)){
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2){
            return null;
        }
        String userId = param[0];
        String token = param[1];
        return new TokenModel(userId, token);
    }

    @Override
    public void deleteToken(String userId) {
        redisUtil.del(userId);
    }
}
