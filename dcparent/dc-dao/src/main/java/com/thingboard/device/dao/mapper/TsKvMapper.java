package com.thingboard.device.dao.mapper;

import com.thingboard.device.datasource.annotation.TargetDataSource;
import com.thingboard.device.datasource.database.DataSourceKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Filename: TsKvMapper
 * @Author: wm
 * @Date: 2019/9/12 9:10
 * @Description:
 * @History:
 */
@Repository
public interface TsKvMapper {

    @TargetDataSource(DataSourceKey.TWO)
    List<Map<String,Object>> listTsKv(Map<String,Object> map);
}
