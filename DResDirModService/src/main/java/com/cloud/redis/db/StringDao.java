package com.cloud.redis.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.redis.db.impl.RedisStringInterface;

@Repository
public class StringDao implements RedisStringInterface {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String setKey(String keyName, String keyValue) throws Exception {
        redisTemplate.opsForValue().set(keyName, keyValue);
        return "success";
    }

    @Override
    public String getKey(String keyName) throws Exception {
        return redisTemplate.opsForValue().get(keyName);
    }

    @Override
    public String getRange(String keyName, Long start, Long end) throws Exception {
        return redisTemplate.opsForValue().get(keyName, start, end);
    }

    @Override
    public String getSetKey(String keyName, String keyValue) throws Exception {
        return redisTemplate.opsForValue().getAndSet(keyName, keyValue);
    }

    @Override
    public Long strLenKey(String keyName) throws Exception {
        return redisTemplate.opsForValue().size(keyName);
    }

    @Override
    public Integer appendKey(String keyName, String keyValue) throws Exception {
        return redisTemplate.opsForValue().append(keyName, keyValue);
    }

}
