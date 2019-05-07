package com.cloud.redis.repository.primary;

import com.cloud.redis.entity.primary.TreeShape;
import com.cloud.redis.projection.TreeShapeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @description: 查询目录
 * @author: ZX
 * @date: 2018/12/18 15:48
 */
public interface TreeShapeRepository extends JpaRepository<TreeShape, Integer>, JpaSpecificationExecutor<TreeShape> {

    /**
     * 根据nodeId查询数据
     *
     * @param uuid
     * @return
     */
    TreeShape findByNodeId(String uuid);

    /**
     * 自定义查询
     *
     * @return
     */
    @Query(value = "SELECT node_id AS nodeId,title,level,pid FROM tree_shape WHERE is_del = 0", nativeQuery = true)
    List<TreeShapeDto> findByTreeShape();

    /**
     * 根据category过滤
     *
     * @param category
     * @return
     */
    @Query(value = "SELECT node_id AS nodeId,title,level,pid FROM tree_shape WHERE is_del = 0 AND (category =?1 OR category IS NULL)", nativeQuery = true)
    List<TreeShapeDto> findByTreeShape(String category);


    /**
     * 查询传感器二级分类
     *
     * @return
     */
    @Query(value = "SELECT node_id as nodeId,title from tree_shape where is_del = 0 and pid=?1", nativeQuery = true)
    List<Map<String, String>> findTreeSecondary(String nodeId);

    /**
     * 查询传感器三级分类
     *
     * @return
     */
    @Query(value = "SELECT node_id as nodeId,title from tree_shape where pid in (SELECT node_id from tree_shape where is_del = 0 and pid=?1)", nativeQuery = true)
    List<Map<String, String>> findTreeThirdly(String nodeId);

    /**
     * 查询传感器nodeId是否存在
     *
     * @return
     */
    @Query(value = "select node_id from tree_shape where pid=0 and tag='thingsboard'", nativeQuery = true)
    List<String> findSersonrId();

}
