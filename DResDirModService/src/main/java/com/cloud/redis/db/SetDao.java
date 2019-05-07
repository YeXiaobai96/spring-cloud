package com.cloud.redis.db;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.redis.db.impl.RedisSetInterface;

@Repository
public class SetDao implements RedisSetInterface {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Long sAddKey(String setName, String... setValue) throws Exception {
        return redisTemplate.opsForSet().add(setName, setValue);
    }

    @Override
    public Long sCardKey(String setName) throws Exception {
        return redisTemplate.opsForSet().size(setName);
    }

    @Override
    public Set<String> sMembersKey(String setName) throws Exception {
        return redisTemplate.opsForSet().members(setName);
    }

    @Override
    public Set<String> sUnionKey(String setName, String setOtherName) throws Exception {
        return redisTemplate.opsForSet().union(setName, setName);
    }

}
