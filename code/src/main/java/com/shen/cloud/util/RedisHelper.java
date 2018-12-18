package com.shen.cloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * create by emmet
 * create at 05/01/2018
 */
@Component
public class RedisHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisHelper.class);

    @Resource private RedisTemplate redisTemplate;


    public boolean set(String key, Object value){
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    public Object get(String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * @param key
     * @param value
     * @param expireTime 秒为单位
     */
    public boolean set(String key, Object value, Long expireTime){
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void del(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @param value
     * @param expireTime 秒为单位
     */
    public boolean setHash(String key, Map<String,String> value, Long expireTime){
        boolean result = false;
        try {
            redisTemplate.opsForHash().putAll(key,value);
            if(expireTime != 0){
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean hashIncrement(String key,String field,Long num){
        boolean result = false;
        try {
            HashOperations<String,String,Object> opsForHash = redisTemplate.opsForHash();
            opsForHash.increment(key,field,num);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param key
     */
    public Map<String,String> getHash(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public Object getHash(String key,String field){
        return redisTemplate.opsForHash().get(key,field);
    }

    /**
     * @param key
     * @param expireTime 秒为单位
     */
    public boolean expireTime(String key,Long expireTime){
        boolean result = false;
        try {
            if(expireTime != 0){
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
