package com.mts.springboot.base.service;

import com.mts.springboot.model.dto.CompanyDto;
import com.mts.springboot.model.entity.TbPage;

import java.util.List;

/**
 * @Filename: TbPageService
 * @Author: wm
 * @Date: 2019/9/25 15:48
 * @Description:
 * @History:
 */
public interface TbPageService {

    int savePage(TbPage record);

    int updatePage(TbPage record);

    int delById(String id);

    List<CompanyDto> listCompany();
}
