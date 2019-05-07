package com.cloud.redis.controller;

import com.cloud.redis.db.impl.SyncImpl;
import com.cloud.redis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Filename: TestController
 * @Author: wm
 * @Date: 2019/4/15 10:53
 * @Description:test
 * @History:
 */
@RefreshScope
@RestController
@RequestMapping("/data")
public class DataController {

    @Value("${neo.hello}")
    private String configTest;

    @Autowired
    private SyncImpl syncImpl;

    @RequestMapping(value = "sync", method = RequestMethod.GET)
    @CacheEvict(value = "RedisCache", allEntries = true)
    public Object sync() {
        syncImpl.synchronization();
        return ResultUtil.success(200, null);
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public Object show() {
        return configTest;
    }

}
