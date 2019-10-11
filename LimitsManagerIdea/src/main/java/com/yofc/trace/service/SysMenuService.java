package com.yofc.trace.service;

import java.util.List;

import com.yofc.trace.entity.SysMenu;
import com.yofc.trace.util.PageUtil;

public interface SysMenuService {
	
	List<SysMenu> findMenuByPidAndUid(String pid,String uid);
	
	SysMenu selectById(String id);
	
	int updateMenu(SysMenu menu);
	
	int insertMenu(SysMenu menu);
	
	int delMenu(SysMenu menu);
	
	int selectByPid(String pid);
	
	PageUtil<SysMenu> findMenuPage(String name,String code,Integer index,Integer size,String mid);
	
	List<SysMenu> selectMenuByPid(String pid);
	
	List<SysMenu> findMenuByUid(String uid);
	
	SysMenu selectByCode(String code);
	
	List<SysMenu> findAll();
}
