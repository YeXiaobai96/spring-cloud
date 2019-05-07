package com.cloud.redis.db.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 定义操作hash的接口
 *
 * @author ZX
 */
public interface RedisHashInterface {

    /**
     * 删除一个或多个哈希表字段
     *
     * @param hashName
     * @param hashKeys
     * @return
     */
    Long hDelKey(String hashName, Object... hashKeys) throws Exception;

    /**
     * 查看哈希表中，指定的字段是否存在
     *
     * @param hashName
     * @param hashKey
     * @return
     */
    Boolean hExistsKey(String hashName, Object hashKey) throws Exception;

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param hashName
     * @param hashKey
     * @return
     */
    Object hGetKey(String hashName, Object hashKey) throws Exception;

    /**
     * 获取所有给定字段的值
     *
     * @param hashName
     * @param hashKeys
     * @return
     */
    List<Object> hmGetKey(String hashName, Collection<Object> hashKeys) throws Exception;

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表中
     *
     * @param hashName
     * @param hashMap
     * @return
     */
    String hmSetKey(String hashName, Map<Object, Object> hashMap) throws Exception;

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     *
     * @param hashName
     * @param hashKey
     * @param hashValue
     * @return
     */
    String hSetKey(String hashName, Object hashKey, Object hashValue) throws Exception;

}
