package com.cloud.redis.controller;

import com.cloud.redis.model.RedisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.redis.db.SortedSetDao;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.util.ResultUtil;

import io.swagger.annotations.ApiOperation;

/**
 * redis控制类：提供对sortedSet操作的接口方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/sortedSet")
public class RedisSortedSetController {
    @Autowired
    private SortedSetDao sortedSetDao;

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "向有序集合添加一个或多个成员，或者更新已存在成员的分数", notes = "用到的参数：setName，setValue，setScore")
    @RequestMapping(value = "/zAdd", method = RequestMethod.POST)
    public Object zAdd(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Boolean zAddKey = sortedSetDao.zAddKey(entity.getSetName(), entity.getSetValue(), entity.getScore());
        return ResultUtil.success(200, zAddKey);
    }

    /**
     * 获取有序集合的成员数
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取有序集合的成员数", notes = "用到的参数：setName")
    @RequestMapping(value = "/zCard", method = RequestMethod.POST)
    public Object zCard(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long zCardKey = sortedSetDao.zCardKey(entity.getSetName());
        return ResultUtil.success(200, zCardKey);
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "计算在有序集合中指定区间分数的成员数", notes = "用到的参数：setName，min，max")
    @RequestMapping(value = "/zCount", method = RequestMethod.POST)
    public Object zCount(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long zCountKey = sortedSetDao.zCountKey(entity.getSetName(), entity.getMin(), entity.getMax());
        return ResultUtil.success(200, zCountKey);
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "移除有序集合中的一个或多个成员", notes = "用到的参数：setName，setValue")
    @RequestMapping(value = "/zRem", method = RequestMethod.POST)
    public Object zRem(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long zRemKey = sortedSetDao.zRemKey(entity.getSetName(), entity.getSetValue());
        return ResultUtil.success(200, zRemKey);
    }
}
