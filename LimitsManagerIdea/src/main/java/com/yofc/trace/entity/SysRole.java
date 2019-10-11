package com.yofc.trace.entity;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {
    private String id;

    private String code;

    private String name;

    private String createdById;

    private Date createdOn;

    private String modifiedById;

    private Date modifiedOn;

    private Integer seq;

    private String memo;

    private Integer status;

    private Integer delFlag;
    
    private Integer isRegRole;

}