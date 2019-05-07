package com.cloud.redis.db;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.redis.db.impl.RedisHashInterface;

/**
 * 提供对hash的具体操作方法
 *
 * @author ZX
 */
@Repository
public class HashDao implements RedisHashInterface {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Long hDelKey(String hashName, Object... hashKeys) throws Exception {
        return redisTemplate.opsForHash().delete(hashName, hashKeys);
    }

    @Override
    public Boolean hExistsKey(String hashName, Object hashKey) throws Exception {
        return redisTemplate.opsForHash().hasKey(hashName, hashKey);
    }

    @Override
    public Object hGetKey(String hashName, Object hashKey) throws Exception {
        return redisTemplate.opsForHash().get(hashName, hashKey);
    }

    @Override
    public List<Object> hmGetKey(String hashName, Collection<Object> hashKeys) throws Exception {
        return redisTemplate.opsForHash().multiGet(hashName, hashKeys);
    }

    @Override
    public String hmSetKey(String hashName, Map<Object, Object> hashMap) throws Exception {
        redisTemplate.opsForHash().putAll(hashName, hashMap);
        return "success";
    }

    @Override
    public String hSetKey(String hashName, Object hashKey, Object hashValue) throws Exception {
        redisTemplate.opsForHash().put(hashName, hashKey, hashValue);
        return "success";
    }

}
