package com.cloud.redis.controller;

import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.model.Result;
import com.cloud.redis.model.SearchParam;
import com.cloud.redis.model.TreeShapeParam;
import com.cloud.redis.projection.NodePageDto;
import com.cloud.redis.repository.primary.NodePageRepository;
import com.cloud.redis.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 搜索类
 * @author: ZX
 * @date: 2018/11/8 9:25
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private NodePageRepository nodePage;

    @ApiOperation(value = "搜索数据集")
    @RequestMapping(value = "/tag", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findTag(@RequestBody SearchParam entity) {
        if (entity.getTag().isEmpty()) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }

        Pageable pageable = PageRequest.of(entity.getPage(), entity.getSize());
        Page<NodePageDto> page = nodePage.findTagByPage(entity.getTag(), pageable);
        return ResultUtil.success(200, page);
    }

    /**
     * 获取数据资源目录层级信息
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取数据资源目录层级信息", notes = "用到的参数：tableName")
    @RequestMapping(value = "/catalog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result findCatalog(@RequestBody TreeShapeParam entity) {
        String tableName = entity.getTableName();
        if (tableName == null || tableName.isEmpty()) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Map<String, String> map = new HashMap<>(16);
        NodePage page = nodePage.findByTableName(tableName);
        NodePage topic = nodePage.findByNodeId(page.getPid());
        NodePage topicGroup = nodePage.findByNodeId(topic.getPid());
        String catalog = topicGroup.getName() + "/" + topic.getName() + "/" + page.getName();
        map.put("nodeId", page.getNodeId());
        map.put("drdirPath", catalog);
        return ResultUtil.success(200, map);
    }
}
