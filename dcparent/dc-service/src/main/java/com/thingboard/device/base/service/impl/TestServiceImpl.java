package com.thingboard.device.base.service.impl;

import com.thingboard.device.base.service.TestService;
import com.thingboard.device.dao.mapper.TestMapper;
import com.thingboard.device.model.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Filename: TestServiceImpl
 * @Author: wm
 * @Date: 2019/9/3 9:14
 * @Description:
 * @History:
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;
    @Override
    public List<Test> list() {
        List<Test> list2=testMapper.list();
        List<Test> list1=testMapper.list1();
        return list1;
    }

    @Override
    public int add(Test test) {
        int b=testMapper.add(test);
        int a=testMapper.add1(test);
        //int c=10/0;
        return b+a;
    }
}
