package com.cloud.redis.db.impl;

public interface RedisStringInterface {
    /**
     * 设置指定String的值
     *
     * @param keyName
     * @param keyValue
     * @return
     */
    String setKey(String keyName, String keyValue) throws Exception;

    /**
     * 获取指定 String的值
     *
     * @param keyName
     * @return
     */
    String getKey(String keyName) throws Exception;

    /**
     * 返回String中字符串值的子字符
     *
     * @param keyName
     * @param start
     * @param end
     * @return
     */
    String getRange(String keyName, Long start, Long end) throws Exception;

    /**
     * 将给定String的值设为 value ，并返回String的旧值
     *
     * @param keyName
     * @return
     */
    String getSetKey(String keyName, String keyValue) throws Exception;

    /**
     * 返回 String所储存的字符串值的长度
     *
     * @param keyName
     * @return
     */
    Long strLenKey(String keyName) throws Exception;

    /**
     * 如果String已经存在并且是一个字符串， 将 value 追加到 String原来的值的末尾
     *
     * @param keyName
     * @param keyValue
     * @return
     */
    Integer appendKey(String keyName, String keyValue) throws Exception;
}
