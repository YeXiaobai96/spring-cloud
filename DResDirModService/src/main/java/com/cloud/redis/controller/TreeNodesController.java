package com.cloud.redis.controller;

import com.cloud.redis.db.RedisDao;
import com.cloud.redis.entity.primary.NodePage;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.model.Result;
import com.cloud.redis.model.TreeShapeParam;
import com.cloud.redis.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * redis控制类：提供树型页面接口调用方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/treeNodes")
public class TreeNodesController {

    @Autowired
    private RedisDao redisDao;

    /**
     * 获取树型目录
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取资源目录树型结构", notes = "用到的参数：treeShape")
    @RequestMapping(value = "/findTreeShape", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result findTreeShape(@RequestBody TreeShapeParam entity) {
        String treeShape = entity.getTreeShape();
        if (treeShape == null || treeShape.isEmpty()) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        List<Object> queryTreeShape = redisDao.queryTreeShape(treeShape, entity.getCategory());
        return ResultUtil.success(200, queryTreeShape);
    }

    /**
     * 添加树型节点页面
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "添加资源目录节点页面", notes = "用到的参数：name，summary，dataSize，alertTime，source，uploadTime，storageType，description，structure，example，other，dataLinkInfo")
    @RequestMapping(value = "/addTreeNodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result addTreeNodes(@RequestBody TreeShapeParam entity) {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String addTreeNodes = redisDao.addTreeNodes(entity);
        return ResultUtil.success(200, addTreeNodes);
    }

    /**
     * 删除树型节点页面
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "删除资源目录节点页面", notes = "用到的参数：nodesId")
    @RequestMapping(value = "/delTreeNodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result delTreeNodes(@RequestBody TreeShapeParam entity) {
        String nodesId = entity.getNodesId();
        if (nodesId == null || nodesId.isEmpty()) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String delTreeNodes = redisDao.delTreeNodes(nodesId);
        return ResultUtil.success(200, delTreeNodes);
    }

    /**
     * 修改树型节点页面的信息
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改资源目录节点页面信息", notes = "用到的参数：name，summary，dataSize，alertTime，source，uploadTime，storageType，description，structure，example，other，dataLinkInfo")
    @RequestMapping(value = "/modifyTreeNodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result modifyTreeNodes(@RequestBody TreeShapeParam entity) {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String modifyTreeNodes = redisDao.modifyTreeNodes(entity);
        return ResultUtil.success(200, modifyTreeNodes);
    }

    /**
     * 获取树型节点页面的信息
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取资源目录节点页面信息", notes = "用到的参数：treeNodes")
    @RequestMapping(value = "/findTreeNodes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result findTreeNodes(@RequestBody TreeShapeParam entity) {
        String treeNodes = entity.getTreeNodes();
        if (treeNodes == null || treeNodes.isEmpty()) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        NodePage queryTreeNodes = redisDao.queryTreeNodes(treeNodes);
        return ResultUtil.success(200, queryTreeNodes);
    }
}
