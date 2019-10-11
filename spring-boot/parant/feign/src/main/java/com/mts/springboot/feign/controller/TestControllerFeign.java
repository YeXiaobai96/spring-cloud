/*
package com.mts.springboot.feign.controller;

import com.mts.springboot.model.entity.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

*/
/**
 * @Filename: TestControllerFeign
 * @Author: wm
 * @Date: 2019/9/24 11:08
 * @Description:
 * @History:
 *//*


@ApiIgnore
@FeignClient(value = "springboot-service")
@RequestMapping("/")
public interface TestControllerFeign {

    @RequestMapping(value = "list",method = RequestMethod.GET)
    Object list();

    @RequestMapping(value = "add",method = RequestMethod.POST)
    Object add(@RequestBody Test test);
}
*/
