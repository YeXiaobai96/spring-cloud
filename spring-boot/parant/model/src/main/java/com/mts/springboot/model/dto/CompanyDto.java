package com.mts.springboot.model.dto;

import com.mts.springboot.model.entity.TbPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Filename: CompanyDto
 * @Author: wm
 * @Date: 2019/9/26 11:10
 * @Description:
 * @History:
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    private String companyId;
    private String companyName;
    private String companyComment;
    private String abbreviation;
    private List<TbPage> pageList;
}
