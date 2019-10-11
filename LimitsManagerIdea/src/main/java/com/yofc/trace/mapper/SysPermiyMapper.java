package com.yofc.trace.mapper;

import java.util.List;

import com.yofc.trace.entity.SysPermiy;

public interface SysPermiyMapper {
	
	int insertPermiy(SysPermiy permiy);
	
	List<String> selectMenuByRoleId(String roleId);
	
	int delPermiy(String roleId);
	
	int delPermiyByMenuId(String menuId);
}