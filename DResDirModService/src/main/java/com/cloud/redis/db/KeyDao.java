package com.cloud.redis.db;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.redis.db.impl.RedisKeyInterface;

/**
 * 提供对key的具体操作方法
 *
 * @author ZX
 */
@Repository
public class KeyDao implements RedisKeyInterface {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean delKey(String keyName) throws Exception {
        return redisTemplate.opsForValue().getOperations().delete(keyName);
    }

    @Override
    public boolean existsKey(String keyName) throws Exception {
        return redisTemplate.opsForValue().getOperations().hasKey(keyName);
    }

    @Override
    public Set<String> patternKey(String keyName) throws Exception {
        return redisTemplate.opsForValue().getOperations().keys(keyName);
    }

    @Override
    public boolean renameKey(String keyOldName, String keyNewName) throws Exception {
        redisTemplate.opsForValue().getOperations().rename(keyOldName, keyNewName);
        return true;
    }

    @Override
    public DataType typeKey(String keyName) throws Exception {
        return redisTemplate.opsForValue().getOperations().type(keyName);
    }

}
