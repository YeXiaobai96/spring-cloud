package com.thingboard.device.base.controller;

import com.thingboard.device.base.service.TestService;
import com.thingboard.device.base.service.TsKvService;
import com.thingboard.device.common.config.ExceptionEnum;
import com.thingboard.device.common.exception.BaseException;
import com.thingboard.device.common.util.GoogleQrcodeUtils;
import com.thingboard.device.common.util.ResultUtil;
import com.thingboard.device.common.util.StringUtil;
import com.thingboard.device.dao.mapper.TestMapper;
import com.thingboard.device.model.entity.Test;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Filename: TestController
 * @Author: wm
 * @Date: 2019/9/2 15:44
 * @Description:
 * @History:
 */
@RestController
public class TestController {


    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private TsKvService tsKvService;


    @RequestMapping("list")
    public Object list() {
        return testMapper.list();
    }

    @RequestMapping("add")
    public Object add(Test test) {
        return testMapper.add(test);
    }

    @RequestMapping("list1")
    public Object list1() {
        return testService.list();
    }

    @RequestMapping("add1")
    public Object add1(Test test) {
        return testService.add(test);
    }

    @Transactional
    @RequestMapping("test2")
    public Object test2(Test test) {
        int b = testMapper.add(test);
        int a = testMapper.add1(test);
        int c = 10 / 0;
        return b;
    }

    @RequestMapping(value = "test3", method = RequestMethod.GET)
    public Object test3() {
        throw new BaseException("haha");
    }


    @RequestMapping(value = "test5", method = RequestMethod.GET)
    public Object test5(String id, Integer index, Integer limit) {
        if (StringUtils.isBlank(id)) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        index = index == null ? 1 : index;
        limit = limit == null ? 10 : limit;
        return tsKvService.listTsKv(id, index, limit);
    }


   /* @RequestMapping("test2")
    public Object img(String img){

        try {
            GoogleQrcodeUtils.encode("www.baidu.com","D:\\img","png",200,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static void main(String[] args) {
        try {
            String fileAddress = "D:\\img\\" + StringUtil.getUUID() + ".png";
            GoogleQrcodeUtils.encode("http://www.baidu.com", fileAddress, "png", 200, false);
            System.out.println(GoogleQrcodeUtils.decode(fileAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
