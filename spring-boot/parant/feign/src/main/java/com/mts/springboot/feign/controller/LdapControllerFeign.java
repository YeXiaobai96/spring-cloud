package com.mts.springboot.feign.controller;

import com.alibaba.fastjson.JSONObject;
import com.mts.springboot.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Filename: LdapControllerFeign
 * @Author: wm
 * @Date: 2019/9/25 11:10
 * @Description:
 * @History: ldap公司查询
 */
@ApiIgnore
@FeignClient(value = "data-ldap")
@RequestMapping("/")
public interface LdapControllerFeign {

    @RequestMapping(value = "searchLdap",method = RequestMethod.POST)
    Result searchLdap(@RequestBody JSONObject map);

}
