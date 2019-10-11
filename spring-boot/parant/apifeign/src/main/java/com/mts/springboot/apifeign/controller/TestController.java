/*
package com.mts.springboot.apifeign.controller;

import com.mts.springboot.feign.controller.TestControllerFeign;
import com.mts.springboot.model.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * @Filename: TestController
 * @Author: wm
 * @Date: 2019/9/24 11:15
 * @Description:
 * @History:
 *//*

@RestController
@RequestMapping("api")
public class TestController {

    @Autowired
    private TestControllerFeign testControllerFeign;

    @RequestMapping(value = "list1",method = RequestMethod.GET)
    public Object list1(){
        return testControllerFeign.list();
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Object add1(@RequestBody Test test){
        return  testControllerFeign.add(test);
    }
}
*/
