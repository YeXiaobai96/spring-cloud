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
public class SysUserRole {
    private String id;

    private String userId;

    private String roleId;

    private String createdById;

    private Date createdOn;

    private String modifiedById;

    private Date modifiedOn;

    private Integer seq;

    private String memo;

    private Integer status;

    private Integer delFlag;
}