package com.cloud.redis.controller;

import com.cloud.redis.model.RedisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.redis.db.StringDao;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.util.ResultUtil;

import io.swagger.annotations.ApiOperation;

/**
 * redis控制类：提供对string操作的接口方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/string")
public class RedisStringController {
    @Autowired
    private StringDao stringDao;

    /**
     * 设置指定 String的值
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "设置指定 String的值", notes = "用到的参数：keyName，keyValue")
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public Object set(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String setKey = stringDao.setKey(entity.getKeyName(), entity.getKeyValue());
        return ResultUtil.success(200, setKey);
    }

    /**
     * 获取指定 String的值
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取指定 String的值", notes = "用到的参数：keyName")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Object get(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String key = stringDao.getKey(entity.getKeyName());
        return ResultUtil.success(200, key);
    }

    /**
     * 返回String中字符串值的子字符
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "返回String中字符串值的子字符", notes = "用到的参数：keyName，start，end")
    @RequestMapping(value = "/getRange", method = RequestMethod.POST)
    public Object getRange(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String range = stringDao.getRange(entity.getKeyName(), entity.getStart(), entity.getEnd());
        return ResultUtil.success(200, range);
    }

    /**
     * 将给定String的值设为 value ，并返回String的旧值
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "将给定String的值设为 value ，并返回String的旧值", notes = "用到的参数：keyName，keyValue")
    @RequestMapping(value = "/getSet", method = RequestMethod.POST)
    public Object getSet(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        String setKey = stringDao.getSetKey(entity.getKeyName(), entity.getKeyValue());
        return ResultUtil.success(200, setKey);
    }

    /**
     * 返回String所储存的字符串值的长度
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "返回String所储存的字符串值的长度", notes = "用到的参数：keyName")
    @RequestMapping(value = "/strLen", method = RequestMethod.POST)
    public Object strLen(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Long strLenKey = stringDao.strLenKey(entity.getKeyName());
        return ResultUtil.success(200, strLenKey);
    }

    /**
     * 如果String已经存在并且是一个字符串， 将 value追加到 String原来的值的末尾
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "如果String已经存在并且是一个字符串， 将 value追加到 String原来的值的末尾", notes = "用到的参数：keyName，keyValue")
    @RequestMapping(value = "/append", method = RequestMethod.POST)
    public Object append(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Integer appendKey = stringDao.appendKey(entity.getKeyName(), entity.getKeyValue());
        return ResultUtil.success(200, appendKey);
    }
}
