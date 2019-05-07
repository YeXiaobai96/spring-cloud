package com.cloud.redis.repository.primary;

import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.projection.NodePageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @description: 查询节点
 * @author: ZX
 * @date: 2018/12/18 15:48
 */
public interface NodePageRepository extends JpaRepository<NodePage, Integer>, JpaSpecificationExecutor<NodePage> {

    /**
     * 根据节点id查询数据
     *
     * @param id
     * @return
     */
    NodePage findByNodeId(String id);

    /**
     * 根据表名查询数据
     *
     * @param tableName
     * @return
     */
    NodePage findByTableName(String tableName);

    /**
     * 排序查询，返回list集合
     *
     * @param id
     * @param sort
     * @return
     */
    List<NodePage> findByNodeId(String id, Sort sort);

    /**
     * 分页查询，返回的是一个片段，它只知道下一片段或者上一片段是否可用。
     *
     * @param pid
     * @param pageable
     * @return
     */
    Slice<NodePage> findByPid(String pid, Pageable pageable);

    /**
     * 分页查询 搜索数据集
     *
     * @param tag
     * @param pageable
     * @return
     */
    @Query(value = "SELECT id,node_id AS nodeId,name,summary,description FROM node_page WHERE is_del = 0 AND (name LIKE %?1% OR summary LIKE %?1%)",
            countQuery = "SELECT COUNT(id) FROM node_page WHERE is_del = 0 AND (name LIKE %?1% OR summary LIKE %?1%)",
            nativeQuery = true)
    Page<NodePageDto> findTagByPage(String tag, Pageable pageable);

    /**
     * 使用原生sql语句查询,?后面数字，对应方法中参数位置
     *
     * @param nodeId
     * @return
     */
    @Query(value = "SELECT t FROM NodePage t WHERE t.isDel = 0 AND t.nodeId = ?1")
    NodePage findNodePage(String nodeId);
}
