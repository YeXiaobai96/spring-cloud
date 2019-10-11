package com.mts.springboot.dao.mapper;

import com.mts.springboot.model.entity.TbPage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbPageMapper {

    int savePage(TbPage record);

    int updatePage(TbPage record);

    int delById(String id);

    List<TbPage> listPage(List<String> list);
}