package com.yofc.trace.service;

import java.util.List;

import com.yofc.trace.entity.SysRole;
import com.yofc.trace.util.PageUtil;

public interface SysRoleService {
	int insertRole(SysRole role);

	int updateRole(SysRole role);

	SysRole selectById(String id);

	SysRole selectByCode(String code);

	PageUtil<SysRole> selectRolePage(Integer index,Integer size,String name,String bTime,String eTime);
	
	int delRole(SysRole role);
	
	int selectRoleCount(String id);
	
	List<SysRole> findRole();
	
	List<SysRole> listRegRole();
}
