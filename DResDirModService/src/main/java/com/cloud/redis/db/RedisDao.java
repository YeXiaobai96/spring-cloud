package com.cloud.redis.db;

import com.cloud.redis.db.impl.RedisEntityInterface;
import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.entity.primary.TreeShape;
import com.cloud.redis.model.TreeShapeParam;
import com.cloud.redis.projection.TreeShapeDto;
import com.cloud.redis.repository.primary.NodePageRepository;
import com.cloud.redis.repository.primary.TreeShapeRepository;
import com.cloud.redis.spec.NodePageSpec;
import com.cloud.redis.spec.TreeShapeSpec;
import com.cloud.redis.util.RedisDaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 仓储类：提供对rides的增、删、改、查操作
 *
 * @author ZX
 */
@Repository
@Service("RedisService")
public class RedisDao implements RedisEntityInterface {
    // 物联网标志
    private static final String Iotd = "Iotd";
    // 企业网标志
    private static final String Entd = "Entd";
    // 互联网标志
    private static final String Intd = "Intd";

    @Autowired
    private TreeShapeRepository treeShapeRepository;
    @Autowired
    private NodePageRepository nodePageRepository;
    @Autowired
    private RedisDaoUtil redisDaoUtil;

    /**
     * 获取树型目录
     *
     * @param shape
     * @param category
     * @return
     */
    @Cacheable(value = "RedisCache")
    @Override
    public List<Object> queryTreeShape(String shape, String category) {
        List<Object> list = new ArrayList<>();
        List<TreeShapeDto> byTreeShape;

        // 根据类型过滤目录
        if (StringUtils.isNotBlank(category)) {
            byTreeShape = treeShapeRepository.findByTreeShape(category);
        } else {
            // 不过滤
            byTreeShape = treeShapeRepository.findByTreeShape();
        }
        for (int i = 0; i < byTreeShape.size(); i++) {
            Map<Object, Object> map = new HashMap<>();
            map.put("id", byTreeShape.get(i).getNodeId());
            map.put("title", byTreeShape.get(i).getTitle());
            map.put("level", byTreeShape.get(i).getLevel());
            map.put("pid", byTreeShape.get(i).getPid());
            list.add(map);
        }
        return list;
    }

    /**
     * 添加树型节点信息
     *
     * @param param
     * @return
     */
    @CacheEvict(value = "RedisCache", allEntries = true)
    @Override
    public String addTreeNodes(TreeShapeParam param) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String pid = param.getPid();

        if (StringUtils.isBlank(pid)) {
            pid = "0";
        }
        if (StringUtils.isNotBlank(param.getNodesId())) {
            uuid = param.getNodesId();
        }
        // 添加树型目录
        TreeShape treeShape = TreeShape.builder()
                .pid(pid)
                .nodeId(uuid)
                .tag(Entd)
                .level(param.getLevel())
                .title(param.getName())
                .category(param.getCategory())
                .redisKey("TreeShape")
                .isDel(0)
                .build();
        treeShapeRepository.save(treeShape);

        // 添加树型节点
        NodePage pageData = redisDaoUtil.treePageData(param, uuid, pid, 0);
        nodePageRepository.save(pageData);

        return "添加成功";
    }

    /**
     * 删除树型节点信息
     *
     * @param key
     * @return
     */
    @CacheEvict(value = "RedisCache", allEntries = true)
    @Override
    public String delTreeNodes(String key) {
        // 删除树型结构表数据
        List<TreeShape> treeShape = treeShapeRepository.findAll(Specification.where(TreeShapeSpec.isNodeIdEqual(key))
                .or(TreeShapeSpec.isPidEqual(key)).and(TreeShapeSpec.isDel(0)));
        for (TreeShape shape : treeShape) {
            shape.setIsDel(1);
            if (shape.getPid().length() > 5) {
                List<TreeShape> node = treeShapeRepository.findAll(
                        Specification.where(TreeShapeSpec.isPidEqual(shape.getNodeId()).and(TreeShapeSpec.isDel(0))));
                for (TreeShape next : node) {
                    next.setIsDel(1);
                }
                treeShapeRepository.saveAll(node);
            }
        }
        treeShapeRepository.saveAll(treeShape);
        // 删除节点页面表数据
        List<NodePage> nodePage = nodePageRepository.findAll(Specification.where(NodePageSpec.isNodeIdEqual(key))
                .or(NodePageSpec.isPidEqual(key)).and(NodePageSpec.isDel(0)));
        for (NodePage page : nodePage) {
            page.setIsDel(1);
            if (page.getPid().length() > 5) {
                List<NodePage> node = nodePageRepository.findAll(
                        Specification.where(NodePageSpec.isPidEqual(page.getNodeId()).and(NodePageSpec.isDel(0))));
                for (NodePage next : node) {
                    next.setIsDel(1);
                }
                nodePageRepository.saveAll(node);
            }
        }
        nodePageRepository.saveAll(nodePage);

        return "删除成功";
    }

    /**
     * 修改树型节点信息
     *
     * @param param
     * @return
     */
    @CacheEvict(value = "RedisCache", allEntries = true)
    @Override
    public String modifyTreeNodes(TreeShapeParam param) {
        // 获取树型结构的key值
        TreeShape treeShape = treeShapeRepository.findByNodeId(param.getId());
        if (treeShape == null) {
            return "无法查询到该节点ID";
        }

        // 修改Mysql数据库的值
        List<TreeShape> treeList = treeShapeRepository
                .findAll(Specification.where(TreeShapeSpec.isNodeIdEqual(param.getId()).and(TreeShapeSpec.isDel(0))));
        for (TreeShape shape : treeList) {
            shape.setTitle(param.getName());
        }
        treeShapeRepository.saveAll(treeList);

        // 修改Mysql数据库的值
        NodePage treePageData = redisDaoUtil.treePageData(param, param.getId(), param.getPid(), 1);
        nodePageRepository.save(treePageData);

        return "修改成功";
    }

    /**
     * 获取树型节点信息
     *
     * @param nodes
     * @return
     */
    @Cacheable(value = "RedisCache")
    @Override
    public NodePage queryTreeNodes(String nodes) {
        return nodePageRepository.findNodePage(nodes);
    }

}
