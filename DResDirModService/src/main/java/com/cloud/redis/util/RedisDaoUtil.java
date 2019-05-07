package com.cloud.redis.util;

import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.model.TreeShapeParam;
import com.cloud.redis.repository.primary.NodePageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 仓储类：工具类
 *
 * @author ZX
 */
@Repository
public class RedisDaoUtil {

    @Autowired
    private NodePageRepository nodePageRepository;

    /**
     * Mysql存储节点页面信息
     *
     * @param hash
     * @param uuid
     * @param pid
     * @return
     */
    public NodePage treePageData(TreeShapeParam hash, String uuid, String pid, Integer type) {
        // type: 0表示新增，1表示修改
        if (type == 0) {
            NodePage node = NodePage.builder()
                    .pid(pid)
                    .nodeId(uuid)
                    .redisKey(uuid)
                    .alertTime(hash.getAlertTime())
                    .category(hash.getCategory())
                    .dataFormat(hash.getDataFormat())
                    .dataLinkInfo(hash.getDataLinkInfo())
                    .dataSize(hash.getDataSize())
                    .description(hash.getDescription())
                    .example(JsonUtil.beanToJson(hash.getExample()))
                    .inParamExplain(hash.getInParamNum())
                    .inParamNum(hash.getInParamNum())
                    .name(hash.getName())
                    .other(hash.getOther())
                    .paramExplain(JsonUtil.beanToJson(hash.getParamExplain()))
                    .paramNum(hash.getParamNum())
                    .reqType(hash.getReqType())
                    .returnExample(hash.getReturnExample())
                    .returnValue(JsonUtil.beanToJson(hash.getReturnValue()))
                    .source(hash.getSource())
                    .storageType(hash.getStorageType())
                    .structure(JsonUtil.beanToJson(hash.getStructure()))
                    .summary(hash.getSummary())
                    .updateFreq(hash.getUpdateFreq())
                    .updateMode(hash.getUpdateMode())
                    .uploadTime(hash.getUploadTime())
                    .tableName(hash.getTableName())
                    .isExistData(hash.getIsExistData())
                    .associatedInfo(JsonUtil.beanToJson(hash.getAssociatedInfo()))
                    .additional(JsonUtil.beanToJson(hash.getAdditional()))
                    .isDel(0)
                    .build();
            return node;
        } else {
            NodePage node = nodePageRepository.findByNodeId(uuid);
            node.setPid(pid);
            node.setNodeId(uuid);
            node.setRedisKey(uuid);
            node.setAlertTime(hash.getAlertTime());
            node.setCategory(hash.getCategory());
            node.setDataFormat(hash.getDataFormat());
            node.setDataLinkInfo(hash.getDataLinkInfo());
            node.setDataSize(hash.getDataSize());
            node.setDescription(hash.getDescription());
            node.setExample(JsonUtil.beanToJson(hash.getExample()));
            node.setInParamExplain(hash.getInParamNum());
            node.setInParamNum(hash.getInParamNum());
            node.setName(hash.getName());
            node.setOther(hash.getOther());
            node.setParamExplain(JsonUtil.beanToJson(hash.getParamExplain()));
            node.setParamNum(hash.getParamNum());
            node.setReqType(hash.getReqType());
            node.setReturnExample(hash.getReturnExample());
            node.setReturnValue(JsonUtil.beanToJson(hash.getReturnValue()));
            node.setSource(hash.getSource());
            node.setStorageType(hash.getStorageType());
            node.setStructure(JsonUtil.beanToJson(hash.getStructure()));
            node.setSummary(hash.getSummary());
            node.setUpdateFreq(hash.getUpdateFreq());
            node.setUpdateMode(hash.getUpdateMode());
            node.setUploadTime(hash.getUploadTime());
            node.setTableName(hash.getTableName());
            node.setIsExistData(hash.getIsExistData());
            node.setAssociatedInfo(JsonUtil.beanToJson(hash.getAssociatedInfo()));
            node.setAdditional(JsonUtil.beanToJson(hash.getAdditional()));
            node.setIsDel(0);
            return node;
        }
    }

}
