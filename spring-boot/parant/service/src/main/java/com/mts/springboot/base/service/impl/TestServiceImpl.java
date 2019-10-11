package com.mts.springboot.base.service.impl;

import com.mts.springboot.base.service.TestService;
import com.mts.springboot.dao.mapper.TestMapper;
import com.mts.springboot.model.entity.Test;
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
        List<Test> list=testMapper.list();
        return list;
    }

    @Override
    public int add(Test test) {
        int b=testMapper.add(test);
        return b;
    }
}
