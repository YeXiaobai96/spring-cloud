package com.yofc.trace.service;

import java.util.List;

import com.yofc.trace.entity.SysPermiy;

public interface SysPermiyService {

	int insertPermiy(SysPermiy permiy);

	List<String> selectMenuByRoleId(String roleId);

	int delPermiy(String roleId);
	
	int delPermiyByMenuId(String menuId);
}
