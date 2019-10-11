package com.mts.springboot.base.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mts.springboot.common.model.Result;
import com.mts.springboot.feign.controller.LdapControllerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @Filename: LdapFeignController
 * @Author: wm
 * @Date: 2019/9/25 11:22
 * @Description:
 * @History:
 */
@ApiIgnore
@RestController
@RequestMapping("ldap")
public class LdapFeignController {

    @Autowired
    private LdapControllerFeign ldapControllerFeign;

    @RequestMapping(value = "searchLdap", method = RequestMethod.POST)
    public Object searchLdap() {
        JSONObject json = new JSONObject();
        json.put("dn", "ou=paas.iot");
        json.put("objectClass", "dcObject");
        Result result = ldapControllerFeign.searchLdap(json);
        JSONArray jsonArray = null;
        if (result.isFlag()) {
            jsonArray = (JSONArray) JSONArray.toJSON(result.getData());
        }
        /*List<String> strings=jsonArray.stream().map(o -> {
            return ((JSONObject)o).getString("postalCode");
        }).collect(Collectors.toList());*/
        return jsonArray;
    }


    public static void main(String[] args) {
        String msg = "{flag=true, code=0, msg=success, data=[{ou=租户1, postalCode=123, objectClass=[organizationalUnit, dcObject], dn=[dc=a.tenant,ou=paas.iot,dc=test,dc=com], l=地址2, dc=a.tenant}, {ou=陈云一, postalCode=f2bd2ea21d647f39a7f3abecada5125, objectClass=[organizationalUnit, dcObject], description=公司简介, dn=[dc=chenyunyi.tenant,ou=paas.iot,dc=test,dc=com], l=南京, dc=chenyunyi.tenant}]}";
    }
}
