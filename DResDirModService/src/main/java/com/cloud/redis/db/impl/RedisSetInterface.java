package com.cloud.redis.db.impl;

import java.util.Set;

public interface RedisSetInterface {

    /**
     * 向集合添加一个或多个成员
     *
     * @param setName
     * @param setValue
     * @return
     */
    Long sAddKey(String setName, String... setValue) throws Exception;

    /**
     * 获取集合的成员数
     *
     * @param setName
     * @return
     */
    Long sCardKey(String setName) throws Exception;

    /**
     * 返回集合中的所有成员
     *
     * @param setName
     * @return
     */
    Set<String> sMembersKey(String setName) throws Exception;

    /**
     * 返回所有给定集合的并集
     *
     * @param setName
     * @return
     */
    Set<String> sUnionKey(String setName, String setOtherName) throws Exception;
}
