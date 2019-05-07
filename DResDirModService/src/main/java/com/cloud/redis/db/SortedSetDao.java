package com.cloud.redis.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.redis.db.impl.RedisSortedSetInterface;

@Repository
public class SortedSetDao implements RedisSortedSetInterface {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Boolean zAddKey(String setName, String setValue, double score) throws Exception {
        return redisTemplate.opsForZSet().add(setName, setValue, score);
    }

    @Override
    public Long zCardKey(String setName) throws Exception {
        return redisTemplate.opsForZSet().size(setName);
    }

    @Override
    public Long zCountKey(String setName, double min, double max) throws Exception {
        return redisTemplate.opsForZSet().count(setName, min, max);
    }

    @Override
    public Long zRemKey(String setName, Object... setValue) throws Exception {
        return redisTemplate.opsForZSet().remove(setName, setValue);
    }

}
