package com.yofc.trace.service;

import com.yofc.trace.entity.SysUserRole;

import java.util.List;
import java.util.Map;

public interface SysUserRoleService {

	int deleteByUserId(String uid);

	int insertSelective(SysUserRole userRole);
	
	int deleteByRoleId(String rid);

	List<Map<String,Object>> listUserRoleById(String uid);

}
