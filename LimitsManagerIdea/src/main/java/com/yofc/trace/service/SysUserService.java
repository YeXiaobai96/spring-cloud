package com.yofc.trace.service;


import com.yofc.trace.entity.SysUser;
import com.yofc.trace.util.PageUtil;

public interface SysUserService {

	PageUtil<SysUser> selectUserPage(String name,Integer index,Integer size);

	SysUser insertUser(SysUser user);

	int updateUser(SysUser user);
	
	int forbidden(SysUser user);
	
	SysUser selectUserByCode(String code);
	
	SysUser selectUserById(String id);
	
	SysUser selectById(String id);
}
