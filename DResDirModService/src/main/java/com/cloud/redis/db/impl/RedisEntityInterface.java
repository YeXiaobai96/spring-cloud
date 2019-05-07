package com.cloud.redis.db.impl;

import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.model.TreeShapeParam;

import java.util.List;

/**
 * 定义redis仓储访问接口
 *
 * @author ZX
 */
public interface RedisEntityInterface {

    /**
     * 获取树型目录
     *
     * @param shape
     * @param category
     * @return
     * @throws Exception
     */
    List<Object> queryTreeShape(String shape, String category) throws Exception;

    /**
     * 添加树型节点页面
     *
     * @param value
     * @return
     * @throws Exception
     */
    String addTreeNodes(TreeShapeParam value) throws Exception;

    /**
     * 删除树型节点页面
     *
     * @param key
     * @return
     * @throws Exception
     */
    String delTreeNodes(String key) throws Exception;

    /**
     * 修改树型节点页面信息
     *
     * @param value
     * @return
     * @throws Exception
     */
    String modifyTreeNodes(TreeShapeParam value) throws Exception;

    /**
     * 获取树型节点
     *
     * @param nodes
     * @return
     * @throws Exception
     */
    NodePage queryTreeNodes(String nodes) throws Exception;

}
