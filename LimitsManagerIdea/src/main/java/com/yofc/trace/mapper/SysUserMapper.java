package com.yofc.trace.mapper;

import java.util.List;
import java.util.Map;

import com.yofc.trace.entity.SysUser;

public interface SysUserMapper {

	List<SysUser> selectUserPage(Map<String, Object> map);
	
	int selectPageCount(Map<String, Object> map);
	
	int insertUser(SysUser user);
	
	int updateUser(SysUser user);
	
	SysUser selectUserByCode(String code);
	
	SysUser selectUserById(String id);
	
	SysUser selectById(String id);
}