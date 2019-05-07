package com.cloud.redis.db.impl;

public interface RedisSortedSetInterface {

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param setName
     * @param score
     * @param score
     * @return
     */
    Boolean zAddKey(String setName, String setValue, double score) throws Exception;

    /**
     * 获取有序集合的成员数
     *
     * @param setName
     * @return
     */
    Long zCardKey(String setName) throws Exception;

    /**
     * 计算在有序集合中指定区间分数的成员数
     *
     * @param setName
     * @param min
     * @param max
     * @return
     */
    Long zCountKey(String setName, double min, double max) throws Exception;

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param listName
     * @param listValue
     * @return
     */
    Long zRemKey(String listName, Object... listValue) throws Exception;
}
