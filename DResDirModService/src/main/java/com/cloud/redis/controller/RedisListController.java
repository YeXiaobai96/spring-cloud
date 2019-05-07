package com.cloud.redis.controller;

import com.cloud.redis.model.RedisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.redis.db.ListDao;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.util.ResultUtil;

import io.swagger.annotations.ApiOperation;

/**
 * redis控制类：提供对list操作的接口方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/list")
public class RedisListController {
    @Autowired
    private ListDao listDao;

    /**
     * 获取列表长度
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取list列表长度", notes = "用到的参数：listName")
    @RequestMapping(value = "/lLen", method = RequestMethod.POST)
    public Object lLen(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long lLenKey = listDao.lLenKey(entity.getListName());
        return ResultUtil.success(200, lLenKey);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "移出并获取list列表的第一个元素", notes = "用到的参数：listName")
    @RequestMapping(value = "/lPop", method = RequestMethod.POST)
    public Object lPop(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String lPopKey = listDao.lPopKey(entity.getListName());
        return ResultUtil.success(200, lPopKey);
    }

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "将一个或多个值插入到list列表头部", notes = "用到的参数：listName，listValue")
    @RequestMapping(value = "/lPush", method = RequestMethod.POST)
    public Object lPush(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long lPushKey = listDao.lPushKey(entity.getListName(), entity.getListValue());
        return ResultUtil.success(200, lPushKey);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "移除并获取list列表最后一个元素", notes = "用到的参数：listName")
    @RequestMapping(value = "/rPop", method = RequestMethod.POST)
    public Object rPop(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String rPopKey = listDao.rPopKey(entity.getListName());
        return ResultUtil.success(200, rPopKey);
    }

    /**
     * 将一个或多个值插入到列表尾部
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "将一个或多个值插入到list列表尾部", notes = "用到的参数：listName，listValue")
    @RequestMapping(value = "/rPush", method = RequestMethod.POST)
    public Object rPush(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long rPushKey = listDao.rPushKey(entity.getListName(), entity.getListValue());
        return ResultUtil.success(200, rPushKey);
    }
}
