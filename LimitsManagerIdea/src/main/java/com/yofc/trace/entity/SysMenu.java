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
public class SysMenu {
    private String id;

    private String icon;

    private String code;

    private String name;

    private Integer node;//0.父节点1.子节点

    private Integer type;

    private String pid;

    private String programUrl;

    private String createdById;

    private Date createdOn;

    private String modifiedById;

    private Date modifiedOn;

    private Integer seq;

    private String memo;

    private Integer status;

    private Integer delFlag;
    
    private String createdUser;

   
}