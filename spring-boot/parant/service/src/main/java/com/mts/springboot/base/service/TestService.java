package com.mts.springboot.base.service;


import com.mts.springboot.model.entity.Test;

import java.util.List;

/**
 * @Filename: TestService
 * @Author: wm
 * @Date: 2019/9/3 9:13
 * @Description:
 * @History:
 */
public interface TestService {

    List<Test> list();

    int add(Test test);
}
