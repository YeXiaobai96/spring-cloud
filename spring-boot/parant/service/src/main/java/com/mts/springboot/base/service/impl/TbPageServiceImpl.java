package com.mts.springboot.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mts.springboot.base.service.TbPageService;
import com.mts.springboot.common.model.Result;
import com.mts.springboot.common.util.StringUtil;
import com.mts.springboot.dao.mapper.TbCompanyPageMapper;
import com.mts.springboot.dao.mapper.TbPageMapper;
import com.mts.springboot.feign.controller.LdapControllerFeign;
import com.mts.springboot.model.dto.CompanyDto;
import com.mts.springboot.model.entity.TbCompanyPage;
import com.mts.springboot.model.entity.TbPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Filename: TbPageServiceImpl
 * @Author: wm
 * @Date: 2019/9/25 15:50
 * @Description:
 * @History:
 */
@Service
public class TbPageServiceImpl implements TbPageService {

    @Autowired
    private TbPageMapper tbPageMapper;

    @Autowired
    private TbCompanyPageMapper tbCompanyPageMapper;

    @Autowired
    private LdapControllerFeign ldapControllerFeign;

    @Override
    public int savePage(TbPage record) {
        String pageId=StringUtil.getUUID();
        record.setId(pageId);
        tbPageMapper.savePage(record);
        TbCompanyPage companyPage = new TbCompanyPage();
        companyPage.setCompanyId(record.getCompanyId());
        companyPage.setPageId(pageId);
        companyPage.setId(StringUtil.getUUID());
        return tbCompanyPageMapper.saveCompanyPage(companyPage);
    }

    @Override
    public int updatePage(TbPage record) {
        return tbPageMapper.updatePage(record);
    }

    @Override
    public int delById(String id) {
        tbCompanyPageMapper.deleteByPageId(id);
        return tbPageMapper.delById(id);
    }

    @Override
    public List<CompanyDto> listCompany() {
        JSONArray jsonArray = searchLdap();
        if(Objects.isNull(jsonArray)||jsonArray.size()==0){
            return null;
        }
        List<String> strings = jsonArray.stream().map(o -> {
            return ((JSONObject) o).getString("postalCode");
        }).collect(Collectors.toList());
        List<TbPage> pageList = tbPageMapper.listPage(strings);
        List<CompanyDto> companyDtos=jsonArray.stream().map(o -> {
            JSONObject json=(JSONObject) o;
            CompanyDto companyDto=new CompanyDto();
            companyDto.setCompanyId(json.getString("postalCode"));
            companyDto.setCompanyName(json.getString("ou"));
            companyDto.setAbbreviation(getFileName(json.getString("dc")));
            companyDto.setCompanyComment(json.getString("description"));
            List<TbPage> pages=pageList.stream().filter(tbPage ->
                tbPage.getCompanyId().equals(companyDto.getCompanyId())
            ).collect(Collectors.toList());
            companyDto.setPageList(pages);
            return companyDto;
        }).collect(Collectors.toList());
        return companyDtos;
    }

    private JSONArray searchLdap() {
        JSONObject json = new JSONObject();
        json.put("dn", "ou=paas.iot");
        json.put("objectClass", "dcObject");
        Result result = ldapControllerFeign.searchLdap(json);
        JSONArray jsonArray = null;
        if (result.isFlag()) {
            jsonArray = (JSONArray) JSONArray.toJSON(result.getData());
        }

        return jsonArray;
    }

    private String getFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos >= 0) {
            return fileName.substring(0, pos);
        }

        return "";
    }
}
