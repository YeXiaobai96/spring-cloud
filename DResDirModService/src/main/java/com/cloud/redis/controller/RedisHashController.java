package com.cloud.redis.controller;

import java.util.Collection;
import java.util.List;

import com.cloud.redis.model.RedisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.redis.db.HashDao;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.util.ResultUtil;

import io.swagger.annotations.ApiOperation;

/**
 * redis控制类：提供对hash操作的接口方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/hash")
public class RedisHashController {

    @Autowired
    private HashDao hashDao;

    /**
     * 删除一个或多个哈希表字段
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "删除一个或多个哈希表字段", notes = "用到的参数：hashName，hashKey")
    @RequestMapping(value = "/hDel", method = RequestMethod.POST)
    public Object hDel(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long hDelKey = hashDao.hDelKey(entity.getHashName(), entity.getHashKey());
        return ResultUtil.success(200, hDelKey);
    }

    /**
     * 查看哈希表中，指定的字段是否存在
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查看哈希表中，指定的字段是否存在", notes = "用到的参数：hashName，hashKey")
    @RequestMapping(value = "/hExists", method = RequestMethod.POST)
    public Object hExists(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Boolean hExistsKey = hashDao.hExistsKey(entity.getHashName(), entity.getHashKey());
        return ResultUtil.success(200, hExistsKey);
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取存储在哈希表中指定字段的值", notes = "用到的参数：hashName，hashKey")
    @RequestMapping(value = "/hGet", method = RequestMethod.POST)
    public Object hGet(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Object hGetKey = hashDao.hGetKey(entity.getHashName(), entity.getHashKey());
        return ResultUtil.success(200, hGetKey);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取所有给定字段的值", notes = "用到的参数：hashName，hashKeys")
    @RequestMapping(value = "/hmGet", method = RequestMethod.POST)
    public Object hmGet(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        List<Object> hmGetKey = hashDao.hmGetKey(entity.getHashName(), (Collection<Object>) entity.getHashKeys());
        return ResultUtil.success(200, hmGetKey);
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表中
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "同时将多个 键值对设置到哈希表中", notes = "用到的参数：hashName，hashMap")
    @RequestMapping(value = "/hmSet", method = RequestMethod.POST)
    public Object hmSet(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String hmSetKey = hashDao.hmSetKey(entity.getHashName(), entity.getHashMap());
        return ResultUtil.success(200, hmSetKey);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改哈希表中的键值对，无则添加", notes = "用到的参数：hashName，hashKey，hashValue")
    @RequestMapping(value = "/hSet", method = RequestMethod.POST)
    public Object hSet(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String hSetKey = hashDao.hSetKey(entity.getHashName(), entity.getHashKey(), entity.getHashValue());
        return ResultUtil.success(200, hSetKey);
    }
}
