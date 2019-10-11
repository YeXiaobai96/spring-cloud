package com.yofc.trace.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    private String id;

    private String orgId;

    private String code;

    private String name;

    private String password;
    
    private String salt;

    private String mobile;

    private Integer isSysUser;

    private String createdById;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdOn;

    private String modifiedById;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedOn;

    private Integer seq;

    private String memo;

    private Integer status;

    private Integer delFlag;
    
    private List<SysRole> roleList;
    
    private String createdUser;
    
    private String modifiedUser;
    
    //删除添加时使用
    private String [] roleIds;
    
    //注册时使用
    private String roleId;
}