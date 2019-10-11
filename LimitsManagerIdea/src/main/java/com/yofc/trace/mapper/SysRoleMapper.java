package com.yofc.trace.mapper;

import java.util.List;
import java.util.Map;

import com.yofc.trace.entity.SysRole;

public interface SysRoleMapper {

	int insertRole(SysRole role);
	
	int updateRole(SysRole role);
	
	SysRole selectById(String id);
	
	SysRole selectByCode(String code);
	
	List<SysRole> selectRolePage(Map<String, Object> map);
	
	int selectPageCount(Map<String, Object> map);
	
	int selectRoleCount(String id);
	
	List<SysRole> findRole();
	
	List<SysRole> listRegRole();
}