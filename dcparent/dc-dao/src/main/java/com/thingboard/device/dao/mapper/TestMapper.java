package com.thingboard.device.dao.mapper;

import com.thingboard.device.datasource.annotation.TargetDataSource;
import com.thingboard.device.datasource.database.DataSourceKey;
import com.thingboard.device.model.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Filename: TestMapper
 * @Author: wm
 * @Date: 2019/9/2 15:42
 * @Description:
 * @History:
 */
@Repository
public interface TestMapper {

    @TargetDataSource(DataSourceKey.TWO)
    List<Test> list();

    @TargetDataSource(DataSourceKey.ONE)
    List<Test> list1();

    @TargetDataSource(DataSourceKey.TWO)
    int add(Test test);

    int add1(Test test);
}
