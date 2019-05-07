package com.cloud.redis.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.redis.db.KeyDao;
import com.cloud.redis.model.RedisParam;
import com.cloud.redis.exception.ExceptionEnum;
import com.cloud.redis.util.ResultUtil;

import io.swagger.annotations.ApiOperation;

/**
 * redis控制类：提供对key操作的接口方法
 *
 * @author ZX
 */
@RestController
@RequestMapping("/Key")
public class RedisKeyController {

    @Autowired
    private KeyDao keyDao;

    /**
     * 删除key
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "删除key值", notes = "用到的参数：keyName")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        boolean delKey = keyDao.delKey(entity.getKeyName());
        return ResultUtil.success(200, delKey);
    }

    /**
     * 检查key是否存在
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "检查key是否存在", notes = "用到的参数：keyName")
    @RequestMapping(value = "/exists", method = RequestMethod.POST)
    public Object exists(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        boolean existsKey = keyDao.existsKey(entity.getKeyName());
        return ResultUtil.success(200, existsKey);
    }

    /**
     * 模糊查询key
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "模糊查询key值", notes = "用到的参数：keyName；参数格式：keyName*")
    @RequestMapping(value = "/pattern", method = RequestMethod.POST)
    public Object pattern(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        Set<String> patternKey = keyDao.patternKey(entity.getKeyName());
        return ResultUtil.success(200, patternKey);
    }

    /**
     * 对key重新命名
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "对key重新命名", notes = "用到的参数：keyOldName，keyNewName")
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public Object rename(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        boolean renameKey = keyDao.renameKey(entity.getKeyOldName(), entity.getKeyNewName());
        return ResultUtil.success(200, renameKey);
    }

    /**
     * 查询key所储存的值的类型
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询key所储存的值的类型", notes = "用到的参数：keyName")
    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public Object type(@RequestBody RedisParam entity) throws Exception {
        if (entity == null) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        DataType typeKey = keyDao.typeKey(entity.getKeyName());
        return ResultUtil.success(200, typeKey);
    }
}
