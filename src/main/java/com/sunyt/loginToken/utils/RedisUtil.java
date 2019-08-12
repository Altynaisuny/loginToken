package com.sunyt.loginToken.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setex(String key, String value, long timeOutSeconds, TimeUnit timeUnit){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeOutSeconds, timeUnit);
    }

    public String getStr(String key){
        return redisTemplate.boundValueOps(key).get();
    }

    public boolean expire(String key,long time, TimeUnit timeUnit){
        try {
            if(time>0){
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
}
