package com.cloud.redis.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.redis.db.impl.RedisListInterface;

/**
 * 提供对List的具体操作方法
 *
 * @author ZX
 */
@Repository
public class ListDao implements RedisListInterface {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Long lLenKey(String listName) throws Exception {
        return redisTemplate.opsForList().size(listName);
    }

    @Override
    public String lPopKey(String listName) throws Exception {
        return redisTemplate.opsForList().leftPop(listName);
    }

    @Override
    public Long lPushKey(String listName, String listValue) throws Exception {
        return redisTemplate.opsForList().leftPush(listName, listValue);
    }

    @Override
    public String rPopKey(String listName) throws Exception {
        return redisTemplate.opsForList().rightPop(listName);
    }

    @Override
    public Long rPushKey(String listName, String listValue) throws Exception {
        return redisTemplate.opsForList().rightPush(listName, listValue);
    }

}
