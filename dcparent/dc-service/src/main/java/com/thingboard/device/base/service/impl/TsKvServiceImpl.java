package com.thingboard.device.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thingboard.device.base.service.TsKvService;
import com.thingboard.device.dao.mapper.TsKvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Filename: TsKvServiceImpl
 * @Author: wm
 * @Date: 2019/9/12 9:12
 * @Description:
 * @History:
 */
@Service
public class TsKvServiceImpl implements TsKvService {

    @Autowired
    private TsKvMapper tsKvMapper;
    @Override
    public PageInfo listTsKv(String id,Integer index,Integer limit) {
        PageHelper.startPage(index,limit);
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Map<String, Object>> list = tsKvMapper.listTsKv(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
