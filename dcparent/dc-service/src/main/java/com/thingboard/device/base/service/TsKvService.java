package com.thingboard.device.base.service;

import com.github.pagehelper.PageInfo;
import com.thingboard.device.datasource.annotation.TargetDataSource;

import java.util.List;
import java.util.Map;

/**
 * @Filename: TsKvService
 * @Author: wm
 * @Date: 2019/9/12 9:12
 * @Description:
 * @History:
 */
public interface TsKvService {


    PageInfo listTsKv(String id, Integer index, Integer limit);
}
