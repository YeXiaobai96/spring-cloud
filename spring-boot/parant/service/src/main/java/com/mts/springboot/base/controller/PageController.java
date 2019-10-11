package com.mts.springboot.base.controller;

import com.mts.springboot.base.service.TbPageService;
import com.mts.springboot.common.config.ExceptionEnum;
import com.mts.springboot.common.util.ResultUtil;
import com.mts.springboot.model.entity.TbPage;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Filename: PageController
 * @Author: wm
 * @Date: 2019/9/26 11:49
 * @Description:
 * @History:
 */
@RestController
@RequestMapping("page")
public class PageController {

    @Autowired
    private TbPageService tbPageService;

    @ApiOperation(value = "添加", notes = "添加客户对应页面")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object savePage(@RequestBody TbPage record) {
        if(StringUtils.isBlank(record.getCompanyId())){
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        int count=tbPageService.savePage(record);
        if(count>0){
            return ResultUtil.success("添加成功");
        }
        return ResultUtil.error(ExceptionEnum.ADD_ERROR);
    }

    @ApiOperation(value = "修改", notes = "修改客户对应页面")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object updatePage(@RequestBody TbPage record) {
        if(StringUtils.isBlank(record.getId())){
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        int count=tbPageService.updatePage(record);
        if(count>0){
            return ResultUtil.success("修改成功");
        }
        return ResultUtil.error(ExceptionEnum.UPDATE_ERROR);
    }

    @ApiOperation(value = "删除", notes = "删除客户对应页面")
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public Object delById(String id) {
        if(StringUtils.isBlank(id)){
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        int count=tbPageService.delById(id);
        if(count>0){
            return ResultUtil.success("删除成功");
        }
        return ResultUtil.error(ExceptionEnum.DEL_ERROR);
    }


    @ApiOperation(value = "查询", notes = "查询客户")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object listCompany() {
        return tbPageService.listCompany();
    }
}
