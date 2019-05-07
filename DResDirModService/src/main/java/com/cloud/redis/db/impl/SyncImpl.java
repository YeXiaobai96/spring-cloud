package com.cloud.redis.db.impl;

import com.cloud.redis.db.SyncDao;
import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.entity.primary.TreeShape;
import com.cloud.redis.repository.primary.NodePageRepository;
import com.cloud.redis.repository.primary.TreeShapeRepository;
import com.cloud.redis.repository.secondary.DeviceRepository;
import com.cloud.redis.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Filename: SyncImpl
 * @Author: wm
 * @Date: 2019/4/15 14:46
 * @Description:同步实现
 * @History:
 */
@Service
public class SyncImpl implements SyncDao {

    @Autowired
    private TreeShapeRepository treeShapeRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private NodePageRepository nodePageRepository;

    @Override
    public void synchronization() {
        String sersonrId = creatFirst();
        syncSecondary(sersonrId);
        syncThirdly(sersonrId);
    }

    /**
     * 同步二级节点和介绍
     */
    public void syncSecondary(String sersonrId) {
        List<String> thingsTypeList = deviceRepository.findDeviceType();
        List<Map<String, String>> treeDtoList = treeShapeRepository.findTreeSecondary(sersonrId);
        List<String> treeTitleList = new ArrayList<>();
        if (treeDtoList != null && treeDtoList.size() != 0) {
            treeDtoList.forEach(tree -> treeTitleList.add(tree.get("title")));
            thingsTypeList.forEach(thingsType -> {
                if (!treeTitleList.contains(thingsType)) {
                    add(thingsType, UUIDUtil.getUUID(), sersonrId);
                }
            });
        } else {
            thingsTypeList.forEach(thingsType -> {
                add(thingsType, UUIDUtil.getUUID(), sersonrId);
            });
        }
    }

    /**
     * 同步三级节点和介绍
     */
    public void syncThirdly(String sersonrId) {
        List<Map<String, Object>> mapList = deviceRepository.findDevice();
        List<Map<String, String>> treeDtoList = treeShapeRepository.findTreeThirdly(sersonrId);
        List<Map<String, String>> sencondaryList = treeShapeRepository.findTreeSecondary(sersonrId);
        List<String> treeTitleList = new ArrayList<>();

        if (treeDtoList != null && treeDtoList.size() != 0) {
            treeDtoList.forEach(tree -> treeTitleList.add(tree.get("nodeId")));
            mapList.forEach(map -> {
                if (!treeTitleList.contains(map.get("id").toString())) {
                    addThird(sencondaryList, map, sersonrId);
                }
            });
        } else {
            mapList.forEach(map -> {
                addThird(sencondaryList, map, sersonrId);
            });
        }
    }

    /**
     * 第三节点操作
     *
     * @param sencondaryList
     * @param map
     */
    public void addThird(List<Map<String, String>> sencondaryList, Map<String, Object> map, String sersonrId) {
        //传感器二级节点信息
        List<Map<String, String>> treeSecondList = sencondaryList.stream()
                .filter(tree -> tree.get("title").equals(map.get("type"))).collect(Collectors.toList());
        Map<String, String> mapNode = null;
        if (treeSecondList != null && treeSecondList.size() != 0) {
            mapNode = treeSecondList.get(0);
            add(map.get("name").toString(), map.get("id").toString(), mapNode.get("nodeId"), map.get("searchtext").toString());
        } else {
            String uuid = UUIDUtil.getUUID();
            //添加二级节点
            add(map.get("type").toString(), uuid, sersonrId);
            //添加三级节点
            add(map.get("name").toString(), map.get("id").toString(), uuid, map.get("searchtext").toString());
        }
    }

    /**
     * 添加二级树和树介绍
     *
     * @param thingsType
     */
    public void add(String thingsType, String uuid, String sersonrId) {
        TreeShape treeShape = TreeShape.builder()
                .nodeId(uuid)
                .category("database").redisKey("TreeShape").title(thingsType).tag("Entd")
                .pid(sersonrId).level(1).isDel(0).build();
        treeShapeRepository.save(treeShape);
        NodePage nodePage = NodePage.builder().pid(sersonrId)
                .redisKey(uuid)
                .alertTime(new Timestamp(System.currentTimeMillis()))
                .uploadTime(new Timestamp(System.currentTimeMillis()))
                .nodeId(uuid).name(thingsType).summary(thingsType).isDel(0).build();
        nodePageRepository.save(nodePage);
    }

    /**
     * 添加三级树和树介绍
     *
     * @param thingsType
     */
    public void add(String thingsType, String thingTypeId, String pid, String searchText) {
        TreeShape treeShape = TreeShape.builder()
                .nodeId(thingTypeId)
                .category("database").redisKey("TreeShape").title(thingsType).tag("Entd")
                .pid(pid).level(2).isDel(0).build();
        treeShapeRepository.save(treeShape);
        NodePage nodePage = NodePage.builder()
                .pid(pid)
                .nodeId(thingTypeId)
                .alertTime(new Timestamp(System.currentTimeMillis()))
                .uploadTime(new Timestamp(System.currentTimeMillis()))
                .name(thingsType)
                .summary(searchText)
                .source("thingsboard")
                .description(searchText)
                .structure(structure)
                .example(example)
                .storageType("PostgreSQL")
                .category("database")
                .tableName("postgresql.thingsboard.ts_kv")
                .redisKey(thingTypeId)
                .isDel(0).build();
        nodePageRepository.save(nodePage);
    }

    /**
     * 创建一级菜单返回nodeId
     *
     * @return
     */
    public String creatFirst() {
        List<String> sList = treeShapeRepository.findSersonrId();

        String sersonrId = sList == null || sList.size() == 0 ? null : sList.get(0);
        if (StringUtils.isBlank(sersonrId)) {
            String uuid = UUIDUtil.getUUID();
            TreeShape treeShape = TreeShape.builder()
                    .nodeId(uuid)
                    .category("database").redisKey("TreeShape").title("传感器").tag("thingsboard")
                    .pid("0").level(1).isDel(0).build();
            treeShapeRepository.save(treeShape);
            NodePage nodePage = NodePage.builder().pid(sersonrId)
                    .redisKey(uuid)
                    .alertTime(new Timestamp(System.currentTimeMillis()))
                    .uploadTime(new Timestamp(System.currentTimeMillis()))
                    .nodeId(uuid).name("传感器").summary("传感器").isDel(0).build();
            nodePageRepository.save(nodePage);
            return uuid;
        }
        return sersonrId;
    }

    private final String structure = "[{\"field\":\"entity_id\",\"type\":\"varchar(32)\",\"comment\":\"主键\",\"remark\":\"\",\"LAY_TABLE_INDEX\":0},{\"field\":\"entity_id\",\"type\":\"varchar(255)\",\"comment\":\"类型\",\"remark\":\"\",\"LAY_TABLE_INDEX\":1},{\"field\":\"key\",\"type\":\"varchar(255)\",\"comment\":\"key值\",\"remark\":\"\",\"LAY_TABLE_INDEX\":2},{\"field\":\"ts\",\"type\":\"timestamp \",\"comment\":\"时间戳\",\"remark\":\"\",\"LAY_TABLE_INDEX\":3},{\"field\":\"bool_v\",\"type\":\"int\",\"comment\":\"bool\",\"remark\":\"\",\"LAY_TABLE_INDEX\":4},{\"field\":\"str_v\",\"type\":\"varchar(255)\",\"comment\":\"key对应str\",\"remark\":\"\",\"LAY_TABLE_INDEX\":5},{\"field\":\"long_v\",\"type\":\"long\",\"comment\":\"key对应long\",\"remark\":\"\",\"LAY_TABLE_INDEX\":6},{\"field\":\"dbl_v\",\"type\":\"decimal(22,2)\",\"comment\":\"key对应double\",\"remark\":\"\",\"LAY_TABLE_INDEX\":7}]";
    private final String example = "[{\"entity_id\":\"1e907453b123e60bd0467c09817c0d2\",\"entity_type\":\"DEVICE\",\"key\":\"turbid\",\"ts\":\"1545668134966\",\"bool_v\":\"\",\"str_v\":\"\",\"long_v\":\"\",\"dbl_v\":\"5.95\"},{\"entity_id\":\"1e9074544cb9000bd0467c09817c0d2\",\"entity_type\":\"DEVICE\",\"key\":\"temperature\",\"ts\":\"1545676686031\",\"bool_v\":\"\",\"str_v\":\"\",\"long_v\":\"\",\"dbl_v\":\"20.06\"},{\"entity_id\":\"1e907452b750d20bd0467c09817c0d2\",\"entity_type\":\"DEVICE\",\"key\":\"PH\",\"ts\":\"1545676686111\",\"bool_v\":\"\",\"str_v\":\"\",\"long_v\":\"\",\"dbl_v\":\"7.04\"},{\"entity_id\":\"1e907453b123e60bd0467c09817c0d2\",\"entity_type\":\"DEVICE\",\"key\":\"turbid\",\"ts\":\"1545676871969\",\"bool_v\":\"\",\"str_v\":\"\",\"long_v\":\"\",\"dbl_v\":\"5.87\"}]";
}
