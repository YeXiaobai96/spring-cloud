package com.cloud.redis.db.impl;

public interface RedisListInterface {

    /**
     * 获取列表长度
     *
     * @param listName
     * @return
     */
    Long lLenKey(String listName) throws Exception;

    /**
     * 移出并获取列表的第一个元素
     *
     * @param listName
     * @return
     */
    String lPopKey(String listName) throws Exception;

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param listName
     * @param listValue
     * @return
     */
    Long lPushKey(String listName, String listValue) throws Exception;

    /**
     * 移除并获取列表最后一个元素
     *
     * @param listName
     * @return
     */
    String rPopKey(String listName) throws Exception;

    /**
     * 将一个或多个值插入到列表尾部
     *
     * @param listName
     * @param listValue
     * @return
     */
    Long rPushKey(String listName, String listValue) throws Exception;
}
