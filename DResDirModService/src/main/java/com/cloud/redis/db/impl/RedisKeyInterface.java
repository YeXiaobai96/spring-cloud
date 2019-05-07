package com.cloud.redis.db.impl;

import org.springframework.data.redis.connection.DataType;

import java.util.Set;

/**
 * 定义操作key的接口
 *
 * @author ZX
 */
public interface RedisKeyInterface {
    /**
     * 删除key
     *
     * @param keyName
     * @return
     */
    boolean delKey(String keyName) throws Exception;

    /**
     * 检查key是否存在
     *
     * @param keyName
     * @return
     */
    boolean existsKey(String keyName) throws Exception;

    /**
     * 模糊查询key
     *
     * @param keyName
     * @return
     */
    Set<String> patternKey(String keyName) throws Exception;

    /**
     * 对key重新命名
     *
     * @param keyOldName
     * @param keyNewName
     * @return
     */
    boolean renameKey(String keyOldName, String keyNewName) throws Exception;

    /**
     * 查询key所储存的值的类型
     *
     * @param keyName
     * @return
     */
    DataType typeKey(String keyName) throws Exception;

}
