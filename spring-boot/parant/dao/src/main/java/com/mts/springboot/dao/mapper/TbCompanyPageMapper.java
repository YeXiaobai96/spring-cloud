package com.mts.springboot.dao.mapper;

import com.mts.springboot.model.entity.TbCompanyPage;
import org.springframework.stereotype.Repository;

@Repository
public interface TbCompanyPageMapper {

    int saveCompanyPage(TbCompanyPage record);

    int deleteByPageId(String id);

}