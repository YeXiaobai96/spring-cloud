package com.thingboard.device.base;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Filename: PageConfig
 * @Author: wm
 * @Date: 2019/7/18 11:18
 * @Description:
 * @History:
 */
@Configuration
public class PageConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "false");
        //配置mysql数据库的方言
        properties.setProperty("dialect", "postgresql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
