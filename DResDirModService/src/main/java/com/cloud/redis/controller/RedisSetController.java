package com.cloud.redis.controller;

import java.util.Set;

import com.cloud.redis.model.RedisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.redis.db.SetDao;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.util.ResultUtil;

import io.swagger.annotations.ApiOperation;

/**
 * redis控制类：提供对set操作的接口方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/set")
public class RedisSetController {
    @Autowired
    private SetDao setDao;

    /**
     * 向集合添加一个或多个成员
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "向set集合添加一个或多个成员", notes = "用到的参数：setName，setValue")
    @RequestMapping(value = "/sAdd", method = RequestMethod.POST)
    public Object sAdd(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long sAddKey = setDao.sAddKey(entity.getSetName(), entity.getSetValue());
        return ResultUtil.success(200, sAddKey);
    }

    /**
     * 获取集合的成员数
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取set集合的成员数", notes = "用到的参数：setName")
    @RequestMapping(value = "/sCard", method = RequestMethod.POST)
    public Object sCard(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long sCardKey = setDao.sCardKey(entity.getSetName());
        return ResultUtil.success(200, sCardKey);
    }

    /**
     * 返回集合中的所有成员
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "返回set集合中的所有成员", notes = "用到的参数：setName")
    @RequestMapping(value = "/sMembers", method = RequestMethod.POST)
    public Object sMembers(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Set<String> sMembersKey = setDao.sMembersKey(entity.getSetName());
        return ResultUtil.success(200, sMembersKey);
    }

    /**
     * 返回所有给定集合的并集
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "返回所有给定set集合的并集", notes = "用到的参数：setName，setOtherName")
    @RequestMapping(value = "/sUnion", method = RequestMethod.POST)
    public Object sUnion(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Set<String> sUnionKey = setDao.sUnionKey(entity.getSetName(), entity.getSetOtherName());
        return ResultUtil.success(200, sUnionKey);
    }
}
